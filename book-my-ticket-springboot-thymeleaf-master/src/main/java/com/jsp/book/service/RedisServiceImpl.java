package com.jsp.book.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.jsp.book.dto.UserDto;
import com.jsp.book.entity.BookedTicket;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

	private static final String USER_DTO_KEY = "dto-";
	private static final String OTP_KEY = "otp-";

	private final Map<String, CacheItem> cache = new ConcurrentHashMap<>();

	private record CacheItem(Object value, Instant expiry) {
		boolean isExpired() {
			return Instant.now().isAfter(expiry);
		}
	}

	@Override
	@Async
	public void saveUserDto(String email, UserDto userDto) {
		cache.put(USER_DTO_KEY + email, new CacheItem(userDto, Instant.now().plus(Duration.ofMinutes(15))));
	}

	@Override
	@Async
	public void saveOtp(String email, int otp) {
		cache.put(OTP_KEY + email, new CacheItem(otp, Instant.now().plus(Duration.ofMinutes(2))));
	}

	@Override
	public UserDto getUserDto(String email) {
		CacheItem item = cache.get(USER_DTO_KEY + email);
		if (item != null && !item.isExpired()) {
			return (item.value() instanceof UserDto dto) ? dto : null;
		}
		return null;
	}

	@Override
	public int getOtp(String email) {
		CacheItem item = cache.get(OTP_KEY + email);
		if (item != null && !item.isExpired()) {
			return (item.value() instanceof Integer otp) ? otp : 0;
		}
		return 0;
	}

	@Override
	public void saveTicket(String orderId, BookedTicket ticket) {
		cache.put(orderId, new CacheItem(ticket, Instant.now().plus(Duration.ofMinutes(15))));
	}

	@Override
	public BookedTicket getTicket(String orderId) {
		CacheItem item = cache.get(orderId);
		if (item != null && !item.isExpired()) {
			return (item.value() instanceof BookedTicket ticket) ? ticket : null;
		}
		return null;
	}
}

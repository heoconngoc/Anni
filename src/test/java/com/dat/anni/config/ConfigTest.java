package com.dat.anni.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

class ConfigTest {

	@Test
	void shouldParseSimpleList() {
		assertEquals(Set.of("chau", "hbc"), Config.parseUsers("chau,hbc"));
	}

	@Test
	void shouldTrimSpacesAroundNames() {
		assertEquals(Set.of("chau", "hbc"), Config.parseUsers("  chau ,  hbc  "));
	}

	@Test
	void shouldLowercaseNames() {
		assertEquals(Set.of("chau"), Config.parseUsers("Chau, CHAU"));
	}

	@Test
	void shouldKeepVietnameseDiacritics() {
		assertEquals(Set.of("châu", "bé"), Config.parseUsers("Châu,Bé"));
	}

	@Test
	void shouldDropEmptySegments() {
		assertEquals(Set.of("be"), Config.parseUsers(",be,,"));
	}

	@Test
	void shouldReturnEmptySetForBlankInput() {
		assertTrue(Config.parseUsers("").isEmpty());
		assertTrue(Config.parseUsers("   ").isEmpty());
		assertTrue(Config.parseUsers(null).isEmpty());
	}

	@Test
	void shouldReplaceNamePlaceholder() {
		assertEquals("Xin chào An!", Config.formatLetter("Xin chào {name}!", "An"));
	}

	@Test
	void shouldConvertLineBreaksToBr() {
		assertEquals("a<br>b<br>c", Config.formatLetter("a\\nb\\nc", "x"));
	}

	@Test
	void shouldHandleNullNameAndTemplate() {
		assertEquals("Hi !", Config.formatLetter("Hi {name}!", null));
		assertEquals("", Config.formatLetter(null, "An"));
	}

	@Test
	void guestLetterFallbackShouldContainPlaceholder() {
		assertTrue(Config.GUEST_LETTER.contains("{name}"));
	}
}

package com.dat.anni.server;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ScoreApiTest {

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	void seedScores() throws Exception {
		submit("dat", "SNAKE", 10);
		submit("dat", "SNAKE", 25);
		submit("lan", "SNAKE", 30);
	}

	private void submit(String user, String game, int score) throws Exception {
		mvc.perform(post("/api/scores")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"%s\",\"gameId\":\"%s\",\"score\":%d}"
								.formatted(user, game, score)))
				.andExpect(status().isOk());
	}

	@Test
	void submitThenTopReturnsMaxPerUserOrdered() throws Exception {
		mvc.perform(get("/api/games/SNAKE/top").param("limit", "3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].username").value("lan"))
				.andExpect(jsonPath("$[0].score").value(30))
				.andExpect(jsonPath("$[1].username").value("dat"))
				.andExpect(jsonPath("$[1].score").value(25));
	}

	@Test
	void bestReturnsUserMaxScore() throws Exception {
		mvc.perform(get("/api/users/dat/best/SNAKE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.best").value(25));
	}

	@Test
	void bestOfUnknownUserIsEmptyZero() throws Exception {
		mvc.perform(get("/api/users/stranger/best/SNAKE"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.best").value(0));
	}

	@Test
	void rejectInvalidPayloads() throws Exception {
		mvc.perform(post("/api/scores")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"\",\"gameId\":\"SNAKE\",\"score\":5}"))
				.andExpect(status().isBadRequest());

		mvc.perform(post("/api/scores")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"a\",\"gameId\":\"SNAKE\",\"score\":-1}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void gamesEndpointListsSharedCatalog() throws Exception {
		mvc.perform(get("/api/games"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value("SNAKE"));
	}
}

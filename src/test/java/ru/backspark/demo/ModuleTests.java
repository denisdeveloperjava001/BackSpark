package ru.backspark.demo;

import org.junit.jupiter.api.Test;
import ru.backspark.demo.controller.SocksController;
import ru.backspark.demo.model.SocksOutcomeParameters;
import ru.backspark.demo.exception.ShortageSocksException;
import ru.backspark.demo.model.Socks;
import ru.backspark.demo.model.SocksDto;
import ru.backspark.demo.model.SocksIncomeParameters;
import ru.backspark.demo.model.SocksUpdateParameters;
import ru.backspark.demo.repository.SocksRepository;
import ru.backspark.demo.service.SocksService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ModuleTests {

	@Test
	void incomeSocksWithoutExistingSocksTest() {
		SocksRepository socksRepository = mock(SocksRepository.class);
		SocksService socksService = new SocksService(socksRepository);
		SocksController socksController = new SocksController(socksService);

		when(socksRepository.findByColorAndCottonPercentage(any(),anyInt())).thenReturn(null);
		when(socksRepository.save(any()))
				.thenReturn(new Socks(UUID.randomUUID(), "red", 50, 101));

		SocksIncomeParameters incomeParameters = new SocksIncomeParameters("red", 50, 101);
		SocksDto socks = socksController.income(incomeParameters);

		assertNotNull(socks);
		assertEquals(socks.getColor(), "red");
		assertEquals(socks.getCottonPercentage(), 50);
		assertEquals(socks.getAmount(), 101);
	}

	@Test
	void incomeSocksWithExistingSocksTest() {
		SocksRepository socksRepository = mock(SocksRepository.class);
		SocksService socksService = new SocksService(socksRepository);
		SocksController socksController = new SocksController(socksService);

		when(socksRepository.findByColorAndCottonPercentage(any(),anyInt()))
				.thenReturn(new Socks(UUID.randomUUID(), "red", 50, 101));
		when(socksRepository.save(any()))
				.thenReturn(new Socks(UUID.randomUUID(), "red", 50, 202));

		SocksIncomeParameters socksIncomeParameters = new SocksIncomeParameters("red", 50, 101);
		SocksDto socksDto = socksController.income(socksIncomeParameters);

		assertNotNull(socksDto);
		assertEquals(socksDto.getColor(), "red");
		assertEquals(socksDto.getCottonPercentage(), 50);
		assertEquals(socksDto.getAmount(), 202);
	}

	@Test
	void outcomeSocksWithExistingSocksTest() {
		SocksRepository socksRepository = mock(SocksRepository.class);
		SocksService socksService = new SocksService(socksRepository);
		SocksController socksController = new SocksController(socksService);

		when(socksRepository.findByColorAndCottonPercentage(anyString(),anyInt()))
				.thenReturn(new Socks(UUID.randomUUID(), "red", 50, 101));
		when(socksRepository.save(any())).thenReturn(new Socks(UUID.randomUUID(), "red", 50, 51));

		SocksOutcomeParameters socksOutcomeParameters = new SocksOutcomeParameters("red", 50, 50);
		SocksDto socksDto = socksController.outcome(socksOutcomeParameters);

		assertNotNull(socksDto);
		assertEquals(socksDto.getColor(), "red");
		assertEquals(socksDto.getCottonPercentage(), 50);
		assertEquals(socksDto.getAmount(), 51);
	}

	@Test
	void outcomeSocksWithoutExistingSocksTest() {
		SocksRepository socksRepository = mock(SocksRepository.class);
		SocksService socksService = new SocksService(socksRepository);
		SocksController socksController = new SocksController(socksService);

		when(socksRepository.findByColorAndCottonPercentage(anyString(),anyInt()))
				.thenReturn(new Socks(UUID.randomUUID(), "red", 50, 101));

		SocksOutcomeParameters socksOutcomeParameters = new SocksOutcomeParameters("red", 50, 500);

		assertThrows(ShortageSocksException.class, () -> socksController.outcome(socksOutcomeParameters));
	}
	@Test
	void updateSocksWithExistingSocksTest() {
		SocksRepository socksRepository = mock(SocksRepository.class);
		SocksService socksService = new SocksService(socksRepository);
		SocksController socksController = new SocksController(socksService);

		UUID socksId = UUID.randomUUID();

		when(socksRepository.findById(any()))
				.thenReturn(Optional.of(new Socks(socksId, "red", 50, 101)));
		when(socksRepository.save(any())).thenReturn(new Socks(socksId, "red", 50, 500));

		SocksUpdateParameters updateParameters = new SocksUpdateParameters("red", 50, 500);
		SocksDto socksDto = socksController.update(socksId, updateParameters);

		assertNotNull(socksDto);
		assertEquals(socksDto.getId(), socksId);
		assertEquals(socksDto.getColor(), "red");
		assertEquals(socksDto.getCottonPercentage(), 50);
		assertEquals(socksDto.getAmount(), 500);
	}

}

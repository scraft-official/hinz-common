package me.hinsinger.hinz.common.random.uuid.impl;

import java.util.UUID;

import me.hinsinger.hinz.common.random.uuid.UUIDGenerationAlgorithm;

public class DefaultRandomUUIDGenerator implements UUIDGenerationAlgorithm {

	@Override
	public UUID generate() {
		return UUID.randomUUID();
	}

}

/*
 * Copyright 2023 - 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ai.autoconfigure.vectorstore.redis;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.RedisVectorStore.RedisVectorStoreConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Christian Tzolov
 */
@AutoConfiguration
@ConditionalOnClass({ RedisVectorStore.class, EmbeddingClient.class })
@EnableConfigurationProperties(RedisVectorStoreProperties.class)
public class RedisVectorStoreAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public RedisVectorStore vectorStore(EmbeddingClient embeddingClient, RedisVectorStoreProperties properties) {

		var config = RedisVectorStoreConfig.builder()
			.withURI(properties.getUri())
			.withIndexName(properties.getIndex())
			.withPrefix(properties.getPrefix())
			.build();

		return new RedisVectorStore(config, embeddingClient);
	}

}

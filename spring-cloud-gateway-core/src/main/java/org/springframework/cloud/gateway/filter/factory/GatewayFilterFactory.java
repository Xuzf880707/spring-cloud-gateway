/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.cloud.gateway.filter.factory;

import java.util.function.Consumer;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.support.Configurable;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.cloud.gateway.support.ShortcutConfigurable;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author Spencer Gibb
 * 用于生成GatewayFilter的接口实现类
 * 同样继承了 ShortcutConfigurable 和 Configurable 接口，支持配置
 */
@FunctionalInterface
public interface GatewayFilterFactory<C> extends ShortcutConfigurable, Configurable<C> {

	String NAME_KEY = "name";
	String VALUE_KEY = "value";

	// useful for javadsl
	default GatewayFilter apply(Consumer<C> consumer) {
		C config = newConfig();
		consumer.accept(config);
		return apply(config);
	}

	default Class<C> getConfigClass() {
		throw new UnsupportedOperationException("getConfigClass() not implemented");
	}

	@Override
	default C newConfig() {
		throw new UnsupportedOperationException("newConfig() not implemented");
	}

	/***
	 * 接收Config配置，生产 GatewayFilter，
	 * @param config
	 * @return
	 * 每一个 GatewayFilterFactory实现类内部都有一个apply方法，用于生产实现GatewayFilter接口的实现类实例
	 */
	GatewayFilter apply(C config);

	default String name() {
		//TODO: deal with proxys
		return NameUtils.normalizeFilterFactoryName(getClass());
	}

	@Deprecated
	default ServerHttpRequest.Builder mutate(ServerHttpRequest request) {
		return request.mutate();
	}
}

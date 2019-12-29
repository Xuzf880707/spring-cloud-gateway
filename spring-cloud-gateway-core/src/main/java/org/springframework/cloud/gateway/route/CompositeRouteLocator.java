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

package org.springframework.cloud.gateway.route;

import reactor.core.publisher.Flux;

/**
 * @author Spencer Gibb
 * 主要用于组合多种的RouteLocator实现类，为映射处理类RoutePredicateHandlerMapping提供统一的处理入口
 */
public class CompositeRouteLocator implements RouteLocator {
	/**
	 * 将组合的 delegates(内部组合了多种RouteLocator实现)
	 */
	private final Flux<RouteLocator> delegates;

	public CompositeRouteLocator(Flux<RouteLocator> delegates) {
		this.delegates = delegates;
	}

	/**
	 * 将组合的 delegates 的路由全部返回
	 * @return
	 */
	@Override
	public Flux<Route> getRoutes() {
		return this.delegates.flatMap(RouteLocator::getRoutes);
	}
}

/*******************************************************************************
 * Copyright (c) 2011 Subgraph.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Subgraph - initial API and implementation
 ******************************************************************************/
package com.subgraph.vega.api.http.requests;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import com.subgraph.vega.api.events.IEventHandler;
import com.subgraph.vega.api.model.macros.IHttpMacro;
import com.subgraph.vega.api.model.requests.IRequestOrigin;

public interface IHttpRequestEngine {
	/**
	 * Get the configuration for this request engine.
	 * 
	 * @return IHttpRequestEngineConfig
	 */
	IHttpRequestEngineConfig getRequestEngineConfig();

	/**
	 * Get the request origin associated with this request engine.
	 * @return Request origin.
	 */
	IRequestOrigin getRequestOrigin();

	/**
	 * Get the HttpClient used by this request engine.
	 * @return HttpClient.
	 */
	HttpClient getHttpClient();
	
	/**
	 * Get the parent HttpContext associated with this request engine. The parent HttpContext is thread-safe and should
	 * be used as the parent for the request HttpContext.
	 * @return Parent HttpContext.
	 */
	HttpContext getHttpContext();
	
	/**
	 * Get the cookie store from the HttpClient instance used by this request engine
	 * 
	 * @return CookieStore instance from HttpClient.
	 */
	CookieStore getCookieStore();
	
	/**
	 * Replace the cookie store for the HttpClient instance used by this request engine.
	 * 
	 * @param cookieStore The new CookieStore to use.
	 */
	void setCookieStore(CookieStore cookieStore);
	
	/**
	 * Register a request modifier.
	 * @param modifier IHttpRequestModifier.
	 */
	void addRequestModifier(IHttpRequestModifier modifier);

	/**
	 * Register an event listener to watch for requests as they are executed by this request engine. Fires:
	 * 	- RequestTaskStartEvent
	 *  - RequestTaskStopEvent
	 * @param listener Event listener.
	 */
	void addRequestListener(IEventHandler listener);

	/**
	 * Deregister a request event listener.
	 * @param listener Event listener.
	 */
	void removeRequestListener(IEventHandler listener);

	/**
	 * Obtain a list of requests in progress.
	 * @return List of requests in progress.
	 */
	IHttpRequestTask[] getRequestList();

	/**
	 * Send a request, returning the request task managing request execution. The provided HttpContext should use this
	 * request engine's HttpContext as its parent.
	 * @param request Request to be send.
	 * @param context HTTP execution context.
	 * @return Request task.
	 */
	IHttpRequestTask sendRequest(HttpUriRequest request, HttpContext context);

	/**
	 * Send a request, returning the request task managing request execution. A HttpContext is automatically generated
	 * for the request using this request engine's HttpContext as its parent.
	 * @param request Request to be send.
	 * @return Request task.
	 */
	IHttpRequestTask sendRequest(HttpUriRequest request);
	
	/**
	 * Create a macro context.
	 * @return Macro context.
	 */
	IHttpMacroContext createMacroContext();
	
	/**
	 * Create a macro executor to execute a macro with this request engine.
	 * @param macro Macro to execute.
	 * @param context Macro context.
	 * @return Macro executor.
	 */
	IHttpMacroExecutor createMacroExecutor(IHttpMacro macro, IHttpMacroContext context);	
}

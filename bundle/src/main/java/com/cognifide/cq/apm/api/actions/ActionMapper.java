/*-
 * ========================LICENSE_START=================================
 * AEM Permission Management
 * %%
 * Copyright (C) 2013 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package com.cognifide.cq.apm.api.actions;

import com.cognifide.cq.apm.api.actions.annotations.Mapping;
import com.cognifide.cq.apm.api.exceptions.ActionCreationException;

import java.lang.reflect.Type;
import java.util.List;

public interface ActionMapper {

	/**
	 * Convert command argument from string to target type
	 */
	Object mapParameter(String parameter, Type type) throws ActionCreationException;

	/**
	 * Describe all possible commands that match specified mapping (produce human readable signatures of
	 * actions)
	 */
	List<String> referMapping(Mapping mapping);
}
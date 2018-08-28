/*
 * ========================LICENSE_START=================================
 * AEM Permission Management
 * %%
 * Copyright (C) 2013 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package com.cognifide.cq.cqsm.core.actions;

import com.cognifide.cq.cqsm.api.actions.annotations.Mapper;
import com.cognifide.cq.cqsm.api.actions.annotations.Mapping;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapperDescriptorFactory {

  public static MapperDescriptor create(Class<?> clazz) {
    Mapper annotation = clazz.getDeclaredAnnotation(Mapper.class);
    if (annotation != null) {
      try {
        Object mapper = clazz.newInstance();
        return new MapperDescriptor(annotation, mapper, getMappings(mapper));
      } catch (InstantiationException e) {
        throw new CannotCreateMapperException("Cannot instantiate action mapper", e);
      } catch (IllegalAccessException e) {
        throw new CannotCreateMapperException("Cannot construct action mapper, is constructor non public?", e);
      }
    }
    throw new CannotCreateMapperException("Cannot find required annotation on given type " + clazz.getName());
  }

  private static List<MappingDescriptor> getMappings(Object mapper) {
    return Arrays.stream(mapper.getClass().getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(Mapping.class))
        .map(MapperDescriptorFactory::createMappingDescriptor)
        .sorted()
        .collect(Collectors.toList());
  }

  private static MappingDescriptor createMappingDescriptor(Method method) {
    Mapping annotation = method.getDeclaredAnnotation(Mapping.class);
    return new MappingDescriptor(annotation, method);
  }
}

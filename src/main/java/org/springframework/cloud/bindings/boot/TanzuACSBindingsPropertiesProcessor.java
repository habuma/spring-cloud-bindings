/*
 * Copyright 2022 the original author or authors.
 *
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
 */
package org.springframework.cloud.bindings.boot;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.cloud.bindings.Binding;
import org.springframework.cloud.bindings.Bindings;
import org.springframework.core.env.Environment;

import static org.springframework.cloud.bindings.boot.Guards.isTypeEnabled;

/**
 * An implementation of {@link BindingsPropertiesProcessor} that detects {@link Binding}s of type: {@value TYPE}.
 */
public class TanzuACSBindingsPropertiesProcessor implements BindingsPropertiesProcessor {

    /**
     * The {@link Binding} type that this processor is interested in: {@value}.
     **/
	public static final String TYPE = "tanzu-acs";

    @Override
    public void process(Environment environment, Bindings bindings, Map<String, Object> properties) {
        if (!isTypeEnabled(environment, TYPE)) {
            return;
        }
        
        bindings.filterBindings(TYPE).forEach(binding -> {
            Map<String, String> secretMap = binding.getSecret();
            Set<Entry<String, String>> entrySet = secretMap.entrySet();
            for (Entry<String, String> entry : entrySet) {
                properties.put(entry.getKey(),  entry.getValue());
            }
        });
    }

}

/*
 * Copyright 2025 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.genai.openai;

import java.io.Serializable;

/** A data class represents chat completion schema. */
@SuppressWarnings("MissingJavadocMethod")
public class ToolCall implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String type;
    private Function function;

    public ToolCall(String id, String type, Function function) {
        this.id = id;
        this.type = type;
        this.function = function;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Function getFunction() {
        return function;
    }

    /** A data class represents chat completion schema. */
    public static final class Function implements Serializable {

        private static final long serialVersionUID = 1L;

        private String arguments;
        private String name;

        public Function(String arguments, String name) {
            this.arguments = arguments;
            this.name = name;
        }

        public String getArguments() {
            return arguments;
        }

        public String getName() {
            return name;
        }
    }
}

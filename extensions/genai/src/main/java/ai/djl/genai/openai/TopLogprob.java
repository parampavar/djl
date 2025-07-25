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

import java.util.List;

/** A data class represents chat completion schema. */
@SuppressWarnings("MissingJavadocMethod")
public class TopLogprob {

    private String token;
    private float logprob;
    private List<Byte> bytes;

    public TopLogprob(String token, float logprob, List<Byte> bytes) {
        this.token = token;
        this.logprob = logprob;
        this.bytes = bytes;
    }

    public String getToken() {
        return token;
    }

    public float getLogprob() {
        return logprob;
    }

    public List<Byte> getBytes() {
        return bytes;
    }
}

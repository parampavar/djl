/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
package ai.djl.huggingface.tokenizers;

import ai.djl.huggingface.tokenizers.jni.CharSpan;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;

import java.util.Arrays;

/** A class holds token encoding information. */
public class Encoding {

    private long[] ids;
    private long[] typeIds;
    private String[] tokens;
    private long[] wordIds;
    private long[] sequenceIds;
    private long[] attentionMask;
    private long[] specialTokenMask;
    private CharSpan[] charTokenSpans;
    private Encoding[] overflowing;
    private boolean exceedMaxLength;

    protected Encoding(
            long[] ids,
            long[] typeIds,
            String[] tokens,
            long[] wordIds,
            long[] sequenceIds,
            long[] attentionMask,
            long[] specialTokenMask,
            CharSpan[] charTokenSpans,
            boolean exceedMaxLength,
            Encoding[] overflowing) {
        this.ids = ids;
        this.typeIds = typeIds;
        this.tokens = tokens;
        this.wordIds = wordIds;
        this.sequenceIds = sequenceIds;
        this.attentionMask = attentionMask;
        this.specialTokenMask = specialTokenMask;
        this.charTokenSpans = charTokenSpans;
        this.exceedMaxLength = exceedMaxLength;
        this.overflowing = overflowing;
    }

    /**
     * Returns the {@link NDList} representation of the encodings.
     *
     * @param encodings the {@code Encoding} batch
     * @param manager the {@link NDManager} to create the NDList
     * @param withTokenType true to include the token type id
     * @param int32 true to use int32 datatype
     * @return the {@link NDList}
     */
    public static NDList toNDList(
            Encoding[] encodings, NDManager manager, boolean withTokenType, boolean int32) {
        NDList list = new NDList();
        if (!int32) {
            long[][] ids = new long[encodings.length][];
            long[][] attentionMask = new long[encodings.length][];
            long[][] typeIds = new long[encodings.length][];
            for (int i = 0; i < encodings.length; i++) {
                ids[i] = encodings[i].getIds();
                attentionMask[i] = encodings[i].getAttentionMask();
                if (withTokenType) {
                    typeIds[i] = encodings[i].getTypeIds();
                }
            }
            list.add(manager.create(ids));
            NDArray inputAttentionMask = manager.create(attentionMask);
            list.add(inputAttentionMask);
            if (withTokenType) {
                list.add(manager.create(typeIds));
            }
            return list;
        }

        int[][] ids = new int[encodings.length][];
        int[][] attentionMask = new int[encodings.length][];
        int[][] typeIds = new int[encodings.length][];
        for (int i = 0; i < encodings.length; i++) {
            ids[i] = Arrays.stream(encodings[i].getIds()).mapToInt(l -> (int) l).toArray();
            attentionMask[i] =
                    Arrays.stream(encodings[i].getAttentionMask()).mapToInt(l -> (int) l).toArray();
            if (withTokenType) {
                typeIds[i] =
                        Arrays.stream(encodings[i].getTypeIds()).mapToInt(l -> (int) l).toArray();
            }
        }
        list.add(manager.create(ids));
        NDArray inputAttentionMask = manager.create(attentionMask);
        list.add(inputAttentionMask);
        if (withTokenType) {
            list.add(manager.create(typeIds));
        }
        return list;
    }

    /**
     * Returns the {@link NDList} representation of the encoding.
     *
     * @param manager the {@link NDManager} to create the NDList
     * @param withTokenType true to include the token type id
     * @param int32 true to use int32 datatype
     * @return the {@link NDList}
     */
    public NDList toNDList(NDManager manager, boolean withTokenType, boolean int32) {
        // Converting encoding to int32 NDList because candle can't convert int64 to fp16 in cuda
        NDList list = new NDList(withTokenType ? 3 : 2);
        if (int32) {
            int[] intIds = Arrays.stream(ids).mapToInt(i -> (int) i).toArray();
            int[] intAttentionMask = Arrays.stream(attentionMask).mapToInt(i -> (int) i).toArray();
            list.add(manager.create(intIds));
            list.add(manager.create(intAttentionMask));
            if (withTokenType) {
                int[] intTypeIds = Arrays.stream(typeIds).mapToInt(i -> (int) i).toArray();
                list.add(manager.create(intTypeIds));
            }
        } else {
            list.add(manager.create(ids));
            list.add(manager.create(attentionMask));
            if (withTokenType) {
                list.add(manager.create(typeIds));
            }
        }
        return list;
    }

    /**
     * Returns the token ids.
     *
     * @return the token ids
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * Returns the token type ids.
     *
     * @return the token type ids
     */
    public long[] getTypeIds() {
        return typeIds;
    }

    /**
     * Returns the tokens.
     *
     * @return the tokens
     */
    public String[] getTokens() {
        return tokens;
    }

    /**
     * Returns the word ids.
     *
     * @return the word ids
     */
    public long[] getWordIds() {
        return wordIds;
    }

    /**
     * Returns the sequence ids.
     *
     * @return the sequence ids
     */
    public long[] getSequenceIds() {
        return sequenceIds;
    }

    /**
     * Returns the attention masks.
     *
     * @return the attention masks
     */
    public long[] getAttentionMask() {
        return attentionMask;
    }

    /**
     * Returns the special token masks.
     *
     * @return the special token masks
     */
    public long[] getSpecialTokenMask() {
        return specialTokenMask;
    }

    /**
     * Returns char token spans.
     *
     * @return char token spans
     */
    public CharSpan[] getCharTokenSpans() {
        return charTokenSpans;
    }

    /**
     * Returns if tokens exceed max length.
     *
     * @return {@code true} if tokens exceed max length
     */
    public boolean exceedMaxLength() {
        return exceedMaxLength;
    }

    /**
     * Returns an array of overflowing encodings.
     *
     * @return the array of overflowing encodings
     */
    public Encoding[] getOverflowing() {
        return overflowing;
    }
}

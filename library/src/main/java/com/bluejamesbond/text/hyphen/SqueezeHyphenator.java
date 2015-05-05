package com.bluejamesbond.text.hyphen;

/*
 * Copyright 2015 Mathew Kurian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * SqueezeHyphenator.java
 * @author Mathew Kurian
 *
 * From TextJustify-Android Library v2.0
 * https://github.com/bluejamesbond/TextJustify-Android
 *
 * Please report any issues
 * https://github.com/bluejamesbond/TextJustify-Android/issues
 *
 * Date: 1/27/15 3:35 AM
 */

import java.util.ArrayList;

public class SqueezeHyphenator implements IHyphenator {

    private static SqueezeHyphenator squeezeHyphenator;

    static {
        squeezeHyphenator = new SqueezeHyphenator();
    }

    private SqueezeHyphenator() {
    }

    public static SqueezeHyphenator getInstance() {
        return squeezeHyphenator;
    }

    @Override
    public ArrayList<String> hyphenate(String word) {

        ArrayList<String> broken = new ArrayList<String>();
        int len = word.length() - 1, i;

        for (i = 0; i < len; i += 2) {
            broken.add(word.substring(i, i + 2));
        }

        if (i < len) {
            broken.add(word.substring(i, word.length()));
        }

        return broken;
    }
}

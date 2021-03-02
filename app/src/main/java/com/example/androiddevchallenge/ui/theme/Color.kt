/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Green200 = Color(0xFFA5D6A7)
val Green300 = Color(0xFF81C784)
val Green500 = Color(0xFF4CAF50)
val Green700 = Color(0xFF388E3C)
val Green800 = Color(0xFF2E7D32)
val Orange300 = Color(0xffFFB74D)
val Orange800 = Color(0xffEF6C00)
val Amber50 = Color(0xFFFFF8E1)
val Amber700 = Color(0xFFFFA000)
val Black800 = Color(0xFF424242)

val Brown100 = Color(0xFFD7CCC8)
val Brown400 = Color(0xFF8D6E63)
val Brown900 = Color(0xFF3E2723)

val BlueGrey100 = Color(0xFFCFD8DC)
val BlueGrey200 = Color(0xFFB0BEC5)
val BlueGrey900 = Color(0xFF263238)

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

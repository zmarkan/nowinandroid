/*
 * Copyright 2022 The Android Open Source Project
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

package com.google.samples.apps.nowinandroid.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

/**
 * A class to model icon tint color values for Now in Android.
 */
@Immutable
data class IconTintColors(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val tertiary: Color = Color.Unspecified
)

/**
 * A composition local for [GradientColors].
 */
val LocalIconTintColors = staticCompositionLocalOf { IconTintColors() }

fun Modifier.niaIconTint(brush: Brush) = then(
    Modifier
        .graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                drawRect(brush, blendMode = BlendMode.SrcAtop)
            }
        }
)

enum class NiaIconTint {
    PRIMARY {
        @Composable
        override fun color(): Color {
            return LocalIconTintColors.current.primary
        }
    },
    SECONDARY {
        @Composable
        override fun color(): Color {
            return LocalIconTintColors.current.secondary
        }
    },
    TERTIARY {
        @Composable
        override fun color(): Color {
            return LocalIconTintColors.current.tertiary
        }
    },
    PRIMARY_SECONDARY {
        @Composable
        override fun brush(): Brush {
            val iconTintColors = LocalIconTintColors.current
            return Brush.linearGradient(
                colors = listOf(iconTintColors.primary, iconTintColors.secondary),
                start = Offset(x = Offset.Infinite.x, y = Offset.Zero.y),
                end = Offset(x = Offset.Zero.x, y = Offset.Infinite.y),
            )
        }
    },
    PRIMARY_TERTIARY {
        @Composable
        override fun brush(): Brush {
            val iconTintColors = LocalIconTintColors.current
            return Brush.linearGradient(
                colors = listOf(iconTintColors.primary, iconTintColors.tertiary),
                start = Offset(x = Offset.Infinite.x, y = Offset.Zero.y),
                end = Offset(x = Offset.Zero.x, y = Offset.Infinite.y),
            )
        }
    };

    @Composable
    open fun brush(): Brush {
        throw NotImplementedError("This NiaIconTint does not provide a brush. Look at color instead.")
    }

    @Composable
    open fun color(): Color {
        throw NotImplementedError("This NiaIconTint does not provide a color. Look at brush instead.")
    }
}
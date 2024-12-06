package dev.blend.util.animations

import kotlin.math.*

/**
 * All easing functions are referred from https://easings.net
 */

class LinearAnimation(duration: Double = 200.0): AbstractAnimation( { a -> a }, duration)

class SineInAnimation(duration: Double = 200.0): AbstractAnimation( { x -> 1 - cos((x * PI) / 2) }, duration)
class SineOutAnimation(duration: Double = 200.0): AbstractAnimation( { x -> sin((x * PI) / 2) }, duration)
class SineInOutAnimation(duration: Double = 200.0): AbstractAnimation( { x -> -(cos(PI * x) - 1) / 2 }, duration)

class CubicOutAnimation(duration: Double = 200.0): AbstractAnimation( { x -> 1 - (1 - x).pow(3.0) }, duration)

class ExpoOutAnimation(duration: Double = 200.0): AbstractAnimation( { x -> if (x == 1.0) 1.0 else 1.0 - 2.0.pow(-10.0 * x) }, duration)

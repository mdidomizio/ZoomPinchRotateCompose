package com.example.zoompinchrotate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.example.zoompinchrotate.ui.theme.ZoomPinchRotateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoomPinchRotateTheme {
                var scale by remember {
                    mutableStateOf(1.0f)
                }

                var rotation by remember {
                    mutableStateOf(0.0f)
                }

                var offset by remember {
                    mutableStateOf(Offset.Zero)
                }

                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1280f / 720f),

                ) {
                    val state = rememberTransformableState {
                        zoomChange, panChange, rotationChange ->
                        scale = (scale * zoomChange).coerceIn(1.0f, 5.0f)

                        /*rotation += rotationChange*/

                        val extraWidth = (scale - 1.0f) * constraints.maxWidth
                        val extraHeight = (scale - 1.0f)* constraints.maxHeight

                        val maxX = extraWidth / 2
                        val maxY = extraHeight / 2

                        offset = Offset(
                            x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                            y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.cat_pic_one),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                /*rotationZ = rotation*/
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .transformable(state)
                    )

                }

            }
        }
    }
}

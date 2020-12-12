package fr.outadoc.aoc.twentynineteen

import fr.outadoc.aoc.scaffold.min
import fr.outadoc.aoc.twentynineteen.Day03.Point
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame

class Day3Debug {

    class DebugCanvas(private val c: Day03.Circuit, private val zoomFactor: Int) : Canvas() {

        private fun Graphics.drawGrid() {
            color = Color.LIGHT_GRAY

            for (x in 0..(c.maxX - c.minX)) {
                drawLine(zoomFactor * x, 0, zoomFactor * x, zoomFactor * (c.maxY - c.minY))
            }

            for (y in 0..(c.maxY - c.minY)) {
                drawLine(0, zoomFactor * y, zoomFactor * (c.maxX - c.minX), zoomFactor * y)
            }
        }

        private fun Graphics.drawWires() {
            val colors = listOf(Color.RED, Color.BLUE, Color.GREEN)

            c.wires.forEachIndexed { i, wire ->
                color = colors[i % colors.size]

                wire.segments.forEach { segment ->
                    drawLine(
                        zoomFactor * (segment.a.x - c.minX),
                        zoomFactor * (segment.a.y - c.minY),
                        zoomFactor * (segment.b.x - c.minX),
                        zoomFactor * (segment.b.y - c.minY)
                    )
                }
            }
        }

        private fun Graphics.drawIntersections() {
            color = Color.BLACK
            val crossSize = 8

            val inter = c.findIntersectionsFast()
            inter.forEach { p ->
                drawOval(
                    zoomFactor * (p.x - c.minX) - (crossSize / 2),
                    zoomFactor * (p.y - c.minY) - (crossSize / 2),
                    crossSize,
                    crossSize
                )
            }

            drawString("found ${inter.size} intersections", 24, 24)

            val closest = inter.map { p ->
                p.distance(Point(0, 0))
            }.min()

            drawString("closest distance: $closest", 24, 48)
        }

        override fun paint(g: Graphics) {
            super.paint(g)

            with(g) {
                drawGrid()
                drawWires()
                drawIntersections()
            }
        }
    }

    fun display(c: Day03.Circuit, zoomFactor: Int = 1) {
        DebugCanvas(c, zoomFactor).also { canvas ->
            canvas.setSize(1024, 1024)

            JFrame("Debug").apply {
                add(canvas)
                pack()
                isVisible = true
            }
        }
    }
}
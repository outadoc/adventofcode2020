package fr.outadoc.aoc.y2019

/*
import java.awt.Canvas
import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

//val input = "R1000,D940,L143,D182,L877,D709,L253,U248,L301,U434,R841,U715,R701,U92,R284,U115,R223,U702,R969,U184,L992,U47,L183,U474,L437,D769,L71,U96,R14,U503,R144,U432,R948,U96,L118,D696,R684,U539,L47,D851,L943,U606,L109,D884,R157,U946,R75,U702,L414,U347,R98,D517,L963,D177,R467,D142,L845,U427,R357,D528,L836,D222,L328,U504,R237,U99,L192,D147,L544,D466,R765,U845,L267,D217,L138,U182,R226,U466,R785,U989,R55,D822,L101,U292,R78,U962,R918,U218,L619,D324,L467,U885,L658,U890,L764,D747,R369,D930,L264,D916,L696,U698,R143,U537,L922,U131,R141,D97,L76,D883,R75,D657,R859,U503,R399,U33,L510,D318,L455,U128,R146,D645,L147,D651,L388,D338,L998,U321,L982,U150,R123,U834,R913,D200,L455,D479,L38,U860,L471,U945,L946,D365,L377,U816,R988,D597,R181,D253,R744,U472,L345,U495,L187,D443,R924,D536,R847,U430,L145,D827,L152,D831,L886,D597,R699,D751,R638,D580,L488,D566,L717,D220,L965,D587,L638,D880,L475,D165,L899,U388,R326,D568,R940,U550,R788,D76,L189,D641,R629,D383,L272,D840,L441,D709,L424,U158,L831,D576,R96,D401,R425,U525,L378,D907,L645,U609,L336,D232,L259,D280,L523,U938,R190,D9,L284,U941,L254,D657,R572,U443,L850,U508,L742,D661,L977,U910,L190,U626,R140,U762,L673,U741,R317,D518,R111,U28,R598,D403,R465,D684,R79,U725,L556,U302,L367,U306,R632,D550,R89,D292,R561,D84,L923,D109,L865,D880,L387,D24,R99,U934,L41,U29,L225,D12,L818,U696,R652,U327,L69,D773,L618,U803,L433,D467,R840,D281,R161,D400,R266,D67,L205,D94,R551,U332,R938,D759,L437,D515,L480,U774,L373,U478,R963,D863,L735,U138,L580,U72,L770,U968,L594\nL990,D248,L833,U137,L556,U943,R599,U481,R963,U812,L825,U421,R998,D847,R377,D19,R588,D657,R197,D354,L548,U849,R30,D209,L745,U594,L168,U5,L357,D135,R94,D686,R965,U838,R192,U428,L861,U354,R653,U543,L633,D508,R655,U575,R709,D53,L801,D709,L92,U289,L466,D875,R75,D448,R576,D972,L77,U4,L267,D727,L3,D687,R743,D830,L803,D537,L180,U644,L204,U407,R866,U886,R560,D848,R507,U470,R38,D652,R806,D283,L836,D629,R347,D679,R609,D224,L131,D616,L687,U181,R539,D829,L598,D55,L806,U208,R886,U794,L268,D365,L145,U690,R50,D698,L140,D512,L551,U845,R351,U724,R405,D245,L324,U181,L824,U351,R223,D360,L687,D640,L653,U158,R786,D962,R931,D151,R939,D34,R610,U684,L694,D283,R402,D253,R388,D195,R732,U809,R246,D571,L820,U742,L507,U967,L886,D693,L273,U558,L914,D122,R146,U788,R83,U149,R241,U616,R326,U40,L192,D845,L577,U803,L668,D443,R705,D793,R443,D883,L715,U757,R767,D360,L289,D756,R696,D236,L525,U872,L332,U203,L152,D234,R559,U191,R340,U926,L746,D128,R867,D562,L100,U445,L489,D814,R921,D286,L378,D956,L36,D998,R158,D611,L493,U542,R932,U957,R55,D608,R790,D388,R414,U670,R845,D394,L572,D612,R842,U792,R959,U7,L285,U769,L410,D940,L319,D182,R42,D774,R758,D457,R10,U82,L861,D901,L310,D217,R644,U305,R92,U339,R252,U460,R609,D486,R553,D798,R809,U552,L183,D238,R138,D147,L343,D597,L670,U237,L878,U872,R789,U268,L97,D313,R22,U343,R907,D646,L36,D516,L808,U622,L927,D982,L810,D149,R390,U101,L565,U488,L588,U426,L386,U305,R503,U227,R969,U201,L698,D850,R800,D961,R387,U632,R543,D541,R750,D174,R543,D237,R487,D932,R220"
val input = "R1000,D940,L143,D182,L877,D709,L253,U248,L301,U434,R841,U715,R701,U92,R284,U115,R223,U702,R969,U184,L992,U47,L183,U474,L437,D769,L71,U96,R14,U503,R144,U432,R948,U96,L118,D696,R684,U539,L47,D851,L943,U606,L109,D884,R157,U946,R75,U702,L414,U347,R98,D517,L963,D177,R467,D142,L845,U427,R357,D528,L836,D222,L328,U504,R237,U99,L192,D147,L544,D466,R765,U845,L267,D217,L138,U182,R226,U466,R785,U989,R55,D822,L101,U292,R78,U962,R918,U218,L619,D324,L467,U885,L658,U890,L764,D747,R369,D930,L264,D916,L696,U698,R143,U537,L922,U131,R141,D97,L76,D883,R75,D657,R859,U503,R399,U33,L510,D318,L455,U128,R146,D645,L147,D651,L388,D338,L998,U321,L982,U150,R123,U834,R913,D200,L455,D479,L38,U860,L471,U945,L946,D365,L377,U816,R988,D597,R181,D253,R744,U472,L345,U495,L187,D443,R924,D536,R847,U430,L145,D827,L152,D831,L886,D597,R699,D751,R638,D580,L488,D566,L717,D220,L965,D587,L638,D880,L475,D165,L899,U388,R326,D568,R940,U550,R788,D76,L189,D641,R629,D383,L272,D840,L441,D709,L424,U158,L831,D576,R96,D401,R425,U525,L378,D907,L645,U609,L336,D232,L259,D280,L523,U938,R190,D9,L284,U941,L254,D657,R572,U443,L850,U508,L742,D661,L977,U910,L190,U626,R140,U762,L673,U741,R317,D518,R111,U28,R598,D403,R465,D684,R79,U725,L556,U302,L367,U306,R632,D550,R89,D292,R561,D84,L923,D109,L865,D880,L387,D24,R99,U934,L41,U29,L225,D12,L818,U696,R652,U327,L69,D773,L618,U803,L433,D467,R840,D281,R161,D400,R266,D67,L205,D94,R551,U332,R938,D759,L437,D515,L480,U774,L373,U478,R963,D863,L735,U138,L580,U72,L770,U968,L594\nL990,D248,L833,U137,L556,U943,R599,U481,R963,U812,L825,U421,R998,D847,R377,D19,R588,D657,R197,D354,L548,U849,R30,D209,L745,U594,L168,U5,L357,D135,R94,D686,R965,U838,R192,U428,L861,U354,R653,U543,L633,D508,R655,U575,R709,D53,L801,D709,L92,U289,L466,D875,R75,D448,R576,D972,L77,U4,L267,D727,L3,D687,R743,D830,L803,D537,L180,U644,L204,U407,R866,U886,R560,D848,R507,U470,R38,D652,R806,D283,L836,D629,R347,D679,R609,D224,L131,D616,L687,U181,R539,D829,L598,D55,L806,U208,R886,U794,L268,D365,L145,U690,R50,D698,L140,D512,L551,U845,R351,U724,R405,D245,L324,U181,L824,U351,R223,D360,L687,D640,L653,U158,R786,D962,R931,D151,R939,D34,R610,U684,L694,D283,R402,D253,R388,D195,R732,U809,R246,D571,L820,U742,L507,U967,L886,D693,L273,U558,L914,D122,R146,U788,R83,U149,R241,U616,R326,U40,L192,D845,L577,U803,L668,D443,R705,D793,R443,D883,L715,U757,R767,D360,L289,D756,R696,D236,L525,U872,L332,U203,L152,D234,R559,U191,R340,U926,L746,D128,R867,D562,L100,U445,L489,D814,R921,D286,L378,D956,L36,D998,R158,D611,L493,U542,R932,U957,R55,D608,R790,D388,R414,U670,R845,D394,L572,D612,R842,U792,R959,U7,L285,U769,L410,D940,L319,D182,R42,D774,R758,D457,R10,U82,L861,D901,L310,D217,R644,U305,R92,U339,R252,U460,R609,D486,R553,D798,R809,U552,L183,D238,R138,D147,L343,D597,L670,U237,L878,U872,R789,U268,L97,D313,R22,U343,R907,D646,L36,D516,L808,U622,L927,D982,L810,D149,R390,U101,L565,U488,L588,U426,L386,U305,R503,U227,R969,U201,L698,D850,R800,D961,R387,U632,R543,D541,R750,D174,R543,D237,R487,D932,R220"
        .lines()

data class Point(val x: Int, val y: Int) {

    operator fun plus(o: Point): Point {
        return Point(x + o.x, y + o.y)
    }

    fun distance(o: Point): Int {
        return abs(x - o.x) + abs(y - o.y)
    }
}

data class Segment(val a: Point, val b: Point) {

    val isVertical = a.x == b.x
    val isHorizontal = a.y == b.y

    val rightMostPoint = if (a.x >= b.x) a else b
    val leftMostPoint = if (a.x >= b.x) b else a
    val topMostPoint = if (a.y >= b.y) b else a
    val bottomMostPoint = if (a.y >= b.y) a else b

    fun isPerpendicularTo(o: Segment): Boolean {
        return when {
            isVertical -> o.isHorizontal
            isHorizontal -> o.isVertical
            else -> throw IllegalStateException("Segment must be aligned on the grid")
        }
    }

    fun intersectsAt(o: Segment): Point? {
        if (!isPerpendicularTo(o)) {
            return null
        }

        val vSeg = if (isVertical) this else o
        val hSeg = if (isHorizontal) this else o

        return when {
            vSeg.topMostPoint.y >= hSeg.a.y ||
                    hSeg.leftMostPoint.x >= vSeg.a.x ||
                    vSeg.bottomMostPoint.y <= hSeg.a.y ||
                    hSeg.rightMostPoint.x <= vSeg.a.x -> {
                null
            }
            else -> {
                Point(hSeg.a.x, vSeg.a.y)
            }
        }
    }
}

data class Wire(val segments: List<Segment>) {

    fun crossesVerticallyAt(x: Int, y: Int): Boolean {
        return segments.any { s ->
            s.a.x == s.b.x // x is constant
                    && s.a.x == x // x is the column that interests us
                    && (y in (s.a.y..s.b.y) || y in (s.b.y..s.a.y)) // the segment exists at row y
        }
    }

    fun crossesHorizontallyAt(x: Int, y: Int): Boolean {
        return segments.any { s ->
            s.a.y == s.b.y // y is constant
                    && s.a.y == y // y is the row that interests us
                    && (x in (s.a.x..s.b.x) || x in (s.b.x..s.a.x)) // the segment exists at column x
        }
    }

    fun intersectsAt(o: Wire): List<Point> {
        return segments.flatMap { s1 ->
            o.segments.mapNotNull { s2 ->
                s1.intersectsAt(s2)
            }
        }
    }
}

data class Circuit(val wires: List<Wire>) {

    val minX: Int by lazy {
        wires.map { w ->
            w.segments.map { s ->
                min(s.a.x, s.b.x)
            }.min() ?: 0
        }.min() ?: 0
    }

    val maxX: Int by lazy {
        wires.map { w ->
            w.segments.map { s ->
                max(s.a.x, s.b.x)
            }.max() ?: 0
        }.max() ?: 0
    }

    val minY: Int by lazy {
        wires.map { w ->
            w.segments.map { s ->
                min(s.a.y, s.b.y)
            }.min() ?: 0
        }.min() ?: 0
    }

    val maxY: Int by lazy {
        wires.map { w ->
            w.segments.map { s ->
                max(s.a.y, s.b.y)
            }.max() ?: 0
        }.max() ?: 0
    }

    private fun checkIntersectionAt(x: Int, y: Int): Boolean {
        val hWiresAtPoint = wires.filter { it.crossesHorizontallyAt(x, y) }
        val vWiresAtPoint = wires.filter { it.crossesVerticallyAt(x, y) }

        if (hWiresAtPoint.isNotEmpty() && vWiresAtPoint.isNotEmpty()
                && hWiresAtPoint != vWiresAtPoint) {
            // wires cross this point both horizontally and vertically
            // and they're not the same one
            return true
        }

        return false
    }

    fun findIntersections(): List<Point> {
        return (minX..maxX).flatMap { x ->
            (minY..maxY).mapNotNull { y ->
                if (checkIntersectionAt(x, y)) {
                    Point(x, y)
                } else null
            }
        }.filterNot { it == Point(0, 0) }
    }

    fun findIntersectionsFast(): List<Point> {
        return wires.flatMap { w1 ->
            wires.flatMap { w2 ->
                w1.intersectsAt(w2)
            }
        }
    }
}

fun parseSegment(str: String, a: Point): Segment {
    val dir = str.first()
    val len = str.drop(1).toInt()

    val b = a + when (dir) {
        'R' -> Point(len, 0)
        'L' -> Point(-len, 0)
        'U' -> Point(0, -len)
        'D' -> Point(0, len)
        else -> throw IllegalArgumentException()
    }

    return Segment(a, b)
}

fun parseWire(wireStr: String): Wire {
    val segments = wireStr.split(',')
            .fold(emptyList<Segment>()) { acc, segmentStr ->
                val lastPoint = acc.lastOrNull()?.b ?: Point(0, 0)
                val wire = parseSegment(segmentStr, lastPoint)
                acc + wire
            }

    return Wire(segments)
}

class DebugCanvas(private val c: Circuit, private val zoomFactor: Int) : Canvas() {

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

fun display(c: Circuit, zoomFactor: Int = 1) {
    DebugCanvas(c, zoomFactor).also { canvas ->
        canvas.setSize(1024, 1024)

        JFrame("Debug").apply {
            add(canvas)
            pack()
            isVisible = true
        }
    }
}

fun step1() {
    val c = Circuit(input.map { parseWire(it) })
    val intersect = c.findIntersectionsFast()

    println(intersect.joinToString())

    //display(c)

    val closest = intersect.map { p -> p.distance(Point(0, 0)) }.min()
    println(closest)
}

step1()
*/
package edu.depauw.scales.graphics

import org.scalajs.dom

import scala.language.implicitConversions

object Base {
  type GraphicsContext = dom.CanvasRenderingContext2D

  type Point = (Double, Double)

  implicit def dd2PathBuilder(p: (Double, Double)): PathBuilder = PathBuilder(p)
  implicit def di2PathBuilder(p: (Double, Int)): PathBuilder = PathBuilder((p._1, p._2))
  implicit def id2PathBuilder(p: (Int, Double)): PathBuilder = PathBuilder((p._1, p._2))
  implicit def ii2PathBuilder(p: (Int, Int)): PathBuilder = PathBuilder((p._1, p._2))

  case class AffineTransform(m11: Double, m12: Double, m21: Double, m22: Double, dx: Double, dy: Double) {
    def apply(p: Point): Point = {
      val (x, y) = p
      (m11 * x + m21 * y + dx, m12 * x + m22 * y + dy)
    }

    def *(that: AffineTransform): AffineTransform =
      AffineTransform(
        m11 * that.m11 + m21 * that.m12,
        m12 * that.m11 + m22 * that.m12,
        m11 * that.m21 + m21 * that.m22,
        m12 * that.m21 + m22 * that.m22,
        m11 * that.dx + m21 * that.dy + dx,
        m12 * that.dx + m22 * that.dy + dy)
  }

  object AffineTransform {
    def getTranslateInstance(dx: Double, dy: Double): AffineTransform =
      AffineTransform(1, 0, 0, 1, dx, dy)

    def getRotateInstance(ang: Angle): AffineTransform =
      AffineTransform(Cos(ang), Sin(ang), -Sin(ang), Cos(ang), 0, 0)
    // TODO special cases for n * 90 deg?

    def getScaleInstance(sx: Double, sy: Double): AffineTransform =
      AffineTransform(sx, 0, 0, sy, 0, 0)
  }

  val Pi = math.Pi
  val Tau = 2 * Pi

  /**
   * Typesafe angle units. A function with an Angle parameter will not accept a Double.
   * Conversions are defined so that, for example, "180 deg" will produce the same Angle
   * as "Pi rad" or "0.5 rev". Value classes (AnyVal) mean this does no object-creation.
   */
  class Angle(val inDegrees: Double) extends AnyVal {
    def inRadians: Double = math.toRadians(inDegrees)

    def inRevolutions: Double = inDegrees / 360

    override def toString: String = inDegrees + " deg"

    def +(a: Angle): Angle = new Angle(inDegrees + a.inDegrees)

    def -(a: Angle): Angle = new Angle(inDegrees - a.inDegrees)

    def *(d: Double): Angle = new Angle(inDegrees * d)

    def /(d: Double): Angle = new Angle(inDegrees / d)
    
    def unary_- : Angle = new Angle(-inDegrees)
  }

  implicit val postfixOps = language.postfixOps // enables us to write "90 deg" without warning

  implicit class AngleFactory(val d: Double) extends AnyVal {
    def deg = new Angle(d)
    def rad = new Angle(math.toDegrees(d))
    def rev = new Angle(d * 360)

    def *(a: Angle): Angle = new Angle(d * a.inDegrees)
  }

  def Sin(ang: Angle): Double = math.sin(ang.inRadians)

  def Cos(ang: Angle): Double = math.cos(ang.inRadians)

  def Tan(ang: Angle): Double = math.tan(ang.inRadians)

  def Atan2(y: Double, x: Double): Angle = math.atan2(y, x) rad

  sealed trait Color {
    def red: Int
    def green: Int
    def blue: Int
    def hue: Angle
    def saturation: Double
    def lightness: Double
    def alpha: Double
  }
  // TODO add various modifiers: moreRed, lessCyan, interpolateHSL, etc.

  case class RGBA(red: Int, green: Int, blue: Int, alpha: Double) extends Color {
    override def toString: String = {
      if (alpha < 1.0) {
        s"rgba($red, $green, $blue, $alpha)"
      } else {
        s"rgb($red, $green, $blue)"
      }
    }

    // Source: http://en.wikipedia.org/wiki/HSL_and_HSV
    def hue: Angle = (60 deg) * (
      if (chroma == 0) 0
      else if (M == green) 2 + (blue - red) / chroma.toDouble
      else if (M == blue) 4 + (red - green) / chroma.toDouble
      else if (green < blue) 6 + (green - blue) / chroma.toDouble
      else (green - blue) / chroma.toDouble)

    def saturation: Double =
      if (chroma == 0) 0
      else chroma / 255.0 / (1 - math.abs(2 * lightness - 1))

    def lightness: Double = (M + m) / 510.0

    private def M: Int = red max green max blue
    private def m: Int = red min green min blue
    private def chroma: Int = M - m
  }

  // TODO normalize hue to 0-360?
  case class HSLA(hue: Angle, saturation: Double, lightness: Double, alpha: Double) extends Color {
    override def toString: String = {
      if (alpha < 1.0) {
        s"hsla(${hue.inDegrees}, ${100 * saturation}%, ${100 * lightness}%, $alpha)"
      } else {
        s"hsl(${hue.inDegrees}, ${100 * saturation}%, ${100 * lightness}%)"
      }
    }

    // Source: http://en.wikipedia.org/wiki/HSL_and_HSV
    def red: Int = math.round(
      if (H < 1 || H >= 5) C + m
      else if (H < 2 || H >= 4) X + m
      else m)

    def green: Int = math.round(
      if (H >= 1 && H < 3) C + m
      else if (H < 4) X + m
      else m)

    def blue: Int = math.round(
      if (H >= 3 && H < 5) C + m
      else if (H >= 2) X + m
      else m)

    private def C: Float = 255 * (1 - math.abs(2 * lightness.toFloat - 1)) * saturation.toFloat
    private def H: Float = hue.inDegrees.toFloat / 60
    private def X: Float = C * (1 - math.abs(H % 2 - 1))
    private def m: Float = 255 * lightness.toFloat - C / 2
  }

  object RGB {
    def apply(red: Int, green: Int, blue: Int): Color =
      RGBA(red, green, blue, 1.0)
  }

  object HSL {
    def apply(hue: Angle, saturation: Double, lightness: Double): Color =
      HSLA(hue, saturation, lightness, 1.0)
  }

  object Color {
    // Top 48 colors from the xkcd color survey
    // (http://blog.xkcd.com/2010/05/03/color-survey-results/),
    // plus White and Clear. Note that these don't always match
    // the corresponding Java or HTML color names. Oh well.
    val White = RGB(0xff, 0xff, 0xff)
    val Purple = RGB(0x7e, 0x1e, 0x9c)
    val Green = RGB(0x15, 0xb0, 0x1a)
    val Blue = RGB(0x03, 0x43, 0xdf)
    val Pink = RGB(0xff, 0x81, 0xc0)
    val Brown = RGB(0x65, 0x37, 0x00)
    val Red = RGB(0xe5, 0x00, 0x00)
    val LightBlue = RGB(0x95, 0xd0, 0xfc)
    val Teal = RGB(0x02, 0x93, 0x86)
    val Orange = RGB(0xf9, 0x73, 0x06)
    val LightGreen = RGB(0x96, 0xf9, 0x7b)
    val Magenta = RGB(0xc2, 0x00, 0x78)
    val Yellow = RGB(0xff, 0xff, 0x14)
    val SkyBlue = RGB(0x75, 0xbb, 0xfd)
    val Grey = RGB(0x92, 0x95, 0x91)
    val LimeGreen = RGB(0x89, 0xfe, 0x05)
    val LightPurple = RGB(0xbf, 0x77, 0xf6)
    val Violet = RGB(0x9a, 0x0e, 0xea)
    val DarkGreen = RGB(0x03, 0x35, 0x00)
    val Turquoise = RGB(0x06, 0xc2, 0xac)
    val Lavender = RGB(0xc7, 0x9f, 0xef)
    val DarkBlue = RGB(0x00, 0x03, 0x5b)
    val Tan = RGB(0xd1, 0xb2, 0x6f)
    val Cyan = RGB(0x00, 0xff, 0xff)
    val Aqua = RGB(0x13, 0xea, 0xc9)
    val ForestGreen = RGB(0x06, 0x47, 0x0c)
    val Mauve = RGB(0xae, 0x71, 0x81)
    val DarkPurple = RGB(0x35, 0x06, 0x3e)
    val BrightGreen = RGB(0x01, 0xff, 0x07)
    val Maroon = RGB(0x65, 0x00, 0x21)
    val Olive = RGB(0x6e, 0x75, 0x0e)
    val Salmon = RGB(0xff, 0x79, 0x6c)
    val Beige = RGB(0xe6, 0xda, 0xa6)
    val RoyalBlue = RGB(0x05, 0x04, 0xaa)
    val NavyBlue = RGB(0x00, 0x11, 0x46)
    val Lilac = RGB(0xce, 0xa2, 0xfd)
    val Black = RGB(0x00, 0x00, 0x00)
    val HotPink = RGB(0xff, 0x02, 0x8d)
    val LightBrown = RGB(0xad, 0x81, 0x50)
    val PaleGreen = RGB(0xc7, 0xfd, 0xb5)
    val Peach = RGB(0xff, 0xb0, 0x7c)
    val OliveGreen = RGB(0x67, 0x7a, 0x04)
    val DarkPink = RGB(0xcb, 0x41, 0x6b)
    val Periwinkle = RGB(0x8e, 0x82, 0xfe)
    val SeaGreen = RGB(0x53, 0xfc, 0xa1)
    val Lime = RGB(0xaa, 0xff, 0x32)
    val Indigo = RGB(0x38, 0x02, 0x82)
    val Mustard = RGB(0xce, 0xb3, 0x01)
    val LightPink = RGB(0xff, 0xd1, 0xdf)

    // Colorless
    val Clear = RGBA(0, 0, 0, 0)
  }
}
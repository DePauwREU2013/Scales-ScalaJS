package edu.depauw.scales.reactive

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.music._
import edu.depauw.scales.act._
import Base._

/*
** Usage example: Not called directly by user
*/
sealed trait Reactive {}

case class CTick(val fps: Double, val dur: Double) extends Reactive 
case class KPress(val key: Key.KeyType) extends Reactive
case class KPressAny() extends Reactive
case class MClick() extends Reactive
case class MPos() extends Reactive

case class MClickX() extends Reactive
case class MClickY() extends Reactive
case class MPosX() extends Reactive
case class MPosY() extends Reactive

case class CTickGetMPos(val fps: Double, val dur: Double) extends Reactive
case class MClickGetCTime() extends Reactive
case class MPosGetCTime() extends Reactive
case class KPressGetCTime(val key: Key.KeyType) extends Reactive
case class KPressAnyGetCTime() extends Reactive

case class CTickChanges(val fps: Double, val dur: Double) extends Reactive
case class KPressChanges(val key: Key.KeyType) extends Reactive
case class KPressAnyChanges() extends Reactive
case class MClickChanges() extends Reactive
case class MPosChanges() extends Reactive

/*
** Usage example: Anim(Reactive.ClockTick(Double, Double), ...)  
** The example above creates a animation that reacts to clock ticks
*/
object Reactive {
	def ClockTick(framesPerSecond: Double, duration: Double) = CTick(framesPerSecond, duration)
	def KeyPress(key: Key.KeyType) = KPress(key)
	def KeyPress() = KPressAny()
	def MouseClick() = MClick()
	def MousePosition() = MPos()

	def MouseClickX() = MClickX()
	def MouseClickY() = MClickY()
	def MousePositionX() = MPosX()
	def MousePositionY() = MPosY()

	def ClockTickGetMousePosition(framesPerSecond: Double, duration: Double) = CTickGetMPos(framesPerSecond, duration)
	def MouseClickGetClockTime() = MClickGetCTime()
	def MousePositionGetClockTime() = MPosGetCTime()
	def KeyPressGetClockTime(key: Key.KeyType) = KPressGetCTime(key)
	def KeyPressGetClockTime() = KPressAnyGetCTime() 

	def ClockTickChanges(framesPerSecond: Double, duration: Double) = CTickChanges(framesPerSecond, duration)
	def KeyPressChanges(key: Key.KeyType) = KPressChanges(key)
	def KeyPressAnyChanges = KPressAnyChanges()
	def MouseClickChanges = MClickChanges()
	def MousePositionChanges = MPosChanges()

}

/*
** Usage example: Anim(Reactive.ClockTick(2, 10), fn)
** @params reaction: a final val from Reactive, such as Reactive.MouseClickGetClockTime
*/
case class Anim[T](reaction: Reactive, fn: T => Scales) extends Scales {
	
	//added - bounds for anim
	//def bounds = RectBounds(l, r, )
	
	//added
	def duration: Double = reaction match {
		case x: CTick => x.dur
		case x: CTickGetMPos => x.dur
		case x: CTickChanges => x.dur
		case _ => 0
	}

	def transformAct(scale: Double): Anim[T] = reaction match {
		case x: CTick => 
			Anim(CTick(x.fps, x.dur * scale), fn)
		case x: CTickGetMPos =>
			Anim(CTickGetMPos(x.fps, x.dur * scale), fn)
		case x: CTickChanges => 
			Anim(CTickChanges(x.fps, x.dur * scale), fn)
		case _ => this 
	}

	lazy val function = fn.asInstanceOf[(Any => Scales)]

	lazy val target: Rx[Any] = reaction match {
		case x: CTick => 
			Timer(x.fps, x.dur).subscribe

		case x: KPress =>
			KeyPress(x.key).subscribe

		case x: KPressAny =>
			Keyboard.subscribe //returns the number

		case x: MClick => 
			MouseClick.subscribe
			
		case x: MPos => 
			MousePosition.subscribe
			
		case x: MClickX => 
			MouseClick.subscribeX
			
		case x: MClickY => 
			MouseClick.subscribeY
			
		case x: MPosX => 
			MousePosition.subscribeX
			
		case x: MPosY => 
			MousePosition.subscribeY
			
		case x: CTickGetMPos => {
			val clock_sub = Timer(x.fps, x.dur).subscribe
			val rx = Var(MousePosition.xy())
			Obs(clock_sub) {
				rx() = MousePosition.xy()
			}
			rx
		}
			
		case x: MClickGetCTime => {
			val click_sub = MouseClick.subscribe
			val startTime = new js.Date().getTime()
			val rx = Var(0.0)
			Obs(click_sub) {
				rx() = (new js.Date().getTime() - startTime) / 1000
			}
			rx
		}
			
		case x: MPosGetCTime => {
			val pos_sub = MousePosition.subscribe
			val startTime = new js.Date().getTime()
			val rx = Var(0.0)
			Obs(pos_sub) {
				rx() = (new js.Date().getTime() - startTime) / 1000
			}
			rx
		}

		case x: KPressGetCTime => {
			val key_sub = KeyPress(x.key).subscribe
			val startTime = new js.Date().getTime()
			val rx = Var(0.0)
			Obs(key_sub) {
				rx() = (new js.Date().getTime() - startTime) / 1000
			}
			rx
		}

		case x: KPressAnyGetCTime => {
			val key_sub = Keyboard.subscribe //rx[Int]
			val startTime = new js.Date().getTime()
			val rx = Var(0.0)
			Obs(key_sub) {
				rx() = (new js.Date().getTime() - startTime) / 1000
			}
			rx
		}

		case x: CTickChanges => 
			Timer(x.fps, x.dur).subscribeChanges

		case x: KPressChanges =>
			KeyPress(x.key).subscribeChanges

		case x: KPressAnyChanges => 
			Keyboard.subscribeChanges

		case x: MClickChanges =>
			MouseClick.subscribeChanges

		case x: MPosChanges =>
			MousePosition.subscribeChanges

		case _ => Var(0)
	}

	lazy val index = Var(-1)

	def initGraphic(g: Graphic): Unit = {
		CanvasHandler.addGraphic(g)
	}

	def unsubscribe(): Unit = {
		target.killAll()
	}

	def act(time: Double = 0): Unit = { 
		
		Obs(target) {
			val result = function(target())

			result match {
				case x: Graphic =>
					if(index() == -1) {
						index() = CanvasHandler.getIndex
						initGraphic(result.asInstanceOf[Graphic])
					} else {
						CanvasHandler.updateGraphic(index(), result.asInstanceOf[Graphic])
					}
				case x: ScalesNote =>
					if(index() == -1) {
						index() = 0
					} else {
						x.act(0)
					}
				case _ =>
			}
		}
	}

}
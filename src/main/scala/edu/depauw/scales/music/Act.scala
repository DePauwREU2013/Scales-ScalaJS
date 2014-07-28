package edu.depauw.scales.music

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._

import Reactive._

// Graphic, Note, and Reactor all extends Scales
trait Scales {
	def act(time: Double = 0): Unit
	def duration: Double	
	def transformAct(scale: Double): Scales
}

trait Performance {
	def act(t: Double = 0): Unit

	def on(that: Performance): ParallelAct = ParallelAct(this, that)

	def seq(that: Performance): SequentialAct = SequentialAct(this, that)

	def transform(scale: Double): Performance

	def length: Double //in seconds
}

/*
** Note, Graphic, and Reactor extend Scales
** Act extends Performance
** Performance handles Scales
*/
//note: took out duration because graphics, notes, and reactors should know their duration
//todo: get actual width and height
case class Act(scales: Scales, val width: Double = Canvas.canvas.width, val height: Double = Canvas.canvas.height) extends Performance {

	def length = scales.duration

	def act(t: Double = 0): Unit = {
		dom.setTimeout(() => scales.act(0), t * 1000)
	}

	def transform(scale: Double): Performance = {
		Act(scales.transformAct(scale), width, height)
	}

}

case class ParallelAct(one: Performance, two: Performance) extends Performance {
	def length = math.max(one.length, two.length)

	def act(t: Double = 0): Unit = {
		dom.setTimeout(() => {
			one.act(0)
			two.act(0)

			// (one, two) match {
			// 	case x:(Graphic, Graphic) =>
			// 		((one.scales) on (two.scales)).act(0) //todo: fix this somehow so that actors, if graphics or reactors, can be piled on top
			// 	case _ =>
			// 		one.act(0)
			// 		two.act(0)
			// }

		}, t * 1000)
	}

	def transform(scale: Double): Performance = {
		ParallelAct(one.transform(scale), two.transform(scale))
	}
}

case class SequentialAct(first: Performance, second: Performance) extends Performance {
	def length = first.length + second.length

	def act(t: Double = 0): Unit = {
		dom.setTimeout(() => {
			first.act(0)
		}, t * 1000)

		val waitDuration = t + first.length
		dom.setTimeout(() => {
			second.act(0)
		}, waitDuration * 1000)

	}

	def transform(scale: Double): Performance = {
		SequentialAct(first.transform(scale), second.transform(scale))
	}
}

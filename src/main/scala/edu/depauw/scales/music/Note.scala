package edu.depauw.scales.music

import scala.scalajs.js
import js.annotation.JSExport
import org.scalajs.dom
import rx._

trait Scales {}

object AudioContext {
	val ctx = js.Dynamic.newInstance(js.Dynamic.global.AudioContext)()
}

trait ScalesNote extends Scales {
	//val ctx = js.Dynamic.newInstance(js.Dynamic.global.AudioContext)()

	def play(t: Double): Unit

	def duration: Double

	def count: Int

	def before(that: ScalesNote): SoundComposite

	def after(that: ScalesNote): SoundComposite

	def par(that: ScalesNote): ParallelComposite

	// def before(that: ScalesNote): SoundComposite = SoundComposite(this, that) //redefining these prob unnecessary

	// def after(that: ScalesNote): SoundComposite = SoundComposite(that, this)

	// def par(that: ScalesNote): ParallelComposite = ParallelComposite(this, that)
}

case class Note(freq: Double, dur: Double = 1, vol: Double = 1) extends ScalesNote {
	val ctx = AudioContext.ctx

	def count = 1

	def duration = dur

	def play(time: Double = 0): Unit = {
		val o = ctx.createOscillator()
		val g = ctx.createGain()

		o.frequency.value = freq
		g.gain.value = vol

		o.connect(g)
		g.connect(ctx.destination)

		val startTime = ctx.currentTime.toString().toDouble + time
		o.start(startTime)
		o.stop(startTime + dur)
	}

	def before(that: ScalesNote): SoundComposite = SoundComposite(this, that)

	def after(that: ScalesNote): SoundComposite = SoundComposite(that, this)

	def par(that: ScalesNote): ParallelComposite = ParallelComposite(this, that)
}

case class SoundComposite(first: ScalesNote, second: ScalesNote) extends ScalesNote {

	val ctx = AudioContext.ctx

	def count = first.count + second.count

	def duration = first.duration + second.duration

	def play(time: Double = 0) {
		val startTime = ctx.currentTime.toString().toDouble + time
		first.play(startTime)
		second.play(startTime + first.duration)
	}

	def before(that: ScalesNote): SoundComposite = SoundComposite(this, that) //redefining these prob unnecessary

	def after(that: ScalesNote): SoundComposite = SoundComposite(that, this)

	def par(that: ScalesNote): ParallelComposite = ParallelComposite(this, that)
}

case class ParallelComposite(first: ScalesNote, second: ScalesNote) extends ScalesNote {
	val ctx = AudioContext.ctx

	def count = first.count + second.count

	def duration = math.max(first.duration, second.duration)

	def play(time: Double = 0) {
		val startTime = ctx.currentTime.toString().toDouble + time
		first.play(startTime)
		second.play(startTime)
	}

	def before(that: ScalesNote): SoundComposite = SoundComposite(this, that) //redefining these prob unnecessary

	def after(that: ScalesNote): SoundComposite = SoundComposite(that, this)

	def par(that: ScalesNote): ParallelComposite = ParallelComposite(this, that)
}

/*
** Default notes start at octave 3
** s signifies sharp
*/
object Silent extends Note(0, 1, 0)
object C extends Note(130.81)
object Cs extends Note(138.59)
object D extends Note(146.83)
object Ds extends Note(155.56)
object E extends Note(164.81)
object F extends Note(174.61)
object Fs extends Note(185)
object G extends Note(196)
object Gs extends Note(207.65)
object A extends Note(220)
object As extends Note(233.08)
object B extends Note(246.94)
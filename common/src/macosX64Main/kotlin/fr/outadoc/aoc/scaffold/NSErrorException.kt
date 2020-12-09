package fr.outadoc.aoc.scaffold

import platform.Foundation.NSError

data class NSErrorException(val nsError: NSError?) : Exception()
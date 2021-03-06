# Lohan

Playground for exploring algorithms and data types. Implemented in Clojure,
mostly as a learning exercise. Code not written for use in real systems, so
don't.

Inspired by [aphyr/meangirls](https://github.com/aphyr/meangirls).


### Identifiers

* [Flake](https://github.com/boundary/flake): 128 bit, K-ordered, decentralised
  identifiers by Boundary
* [Snowflake](https://github.com/boundary/flake): 64 bit, K-ordered,
  decentralised identifiers by Twitter

### CRDTs

#### Sets

* [G-set](https://github.com/aphyr/meangirls#g-set)
* [2P-set](https://github.com/aphyr/meangirls#2p-set)
* [LWW-element set](https://github.com/aphyr/meangirls#lww-element-set)
* [OR-set](https://github.com/aphyr/meangirls#or-set)

#### Counters

* [G-counter](https://github.com/aphyr/meangirls#g-counter)
* [PN-counter](https://github.com/aphyr/meangirls#pn-counter)

### Agreement algorithms

* [Marzullo's algorithm](http://en.wikipedia.org/wiki/Marzullo%27s_algorithm):
  find best value from a number of noisy sources. Find the best intersect from
  a number of ranges.
* [Intersection algorithm](http://en.wikipedia.org/wiki/Intersection_algorithm):
  Improvement on Marzullo's algorithm, handles conflicting ranges better.
  The algorithm used in NTP to find the best matching clock range.

### Distributions

* [Zipf distribution](http://en.wikipedia.org/wiki/Zipf's_law):
  Sample from Zipf distributions, a power law distribution commonly useful for
  web loads, e.g. page hits for a site

## Usage

    $ lein test

## License

Copyright © 2013 Niklas Gustavsson

Distributed under Apache License version 2

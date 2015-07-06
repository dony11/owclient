# Introduction #

Using a recursive feedback algorithm OwClient ensures:
  * Network loop avoidance
  * A device is always represented on the actual physical hub port it is attached to
  * A device is always read using the shortest possible path

## Background ##

A 1-wire network in is simplest form consists of a single wire, with all sensors attached to this wire in parallel. This network topology exhibits undesirable electrical properties, and makes it very impractical to connect a sensors that are spread out geographically.

As a solution hubs can be introduced on a 1-wire network which improves electrical characteristics and (maybe more importantly) enables networks with star topology which are far easier to wire.

Hubs on a 1-wire network are typically implemented using DS2409 microLan couplers (family code 1F), and in common 1-wire hub implementations (e.g. [Hobby Boards 6 Channel Master Hub](http://www.hobby-boards.com/catalog/product_info.php?products_id=1512)) they introduce loops on the hub ports. Any port on these hubs will show attached devices as well as all ports on the hub.

A 6 port hub contains three DS2409 devices and a 1-wire bus with this hub attached will show the following devices attached:
```
1F.xxxxxxx1
1F.xxxxxxx2
1F.xxxxxxx3
....
```
If the first DS2409 device has a temperature sensor DS18S20 (family code 10) attached, the first DS2409 will respond with the following:
```
10.xxxxxxxx
1F.xxxxxxx1
1F.xxxxxxx2
1F.xxxxxxx3
....
```

The consequences of this hub design are:
  * There is a path from any port on the hub to any device attached to the hub, and not just that port
  * An unlimited number of unlimited paths can be constructed from each port because each port on the hub will always include all ports (itself included) in it's response.

# Implementation #

Needs to be written ....
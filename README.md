Artifacts are things that can be looked up by an id, it is as simple as that.

In the beginning there were types, they could be found via SPI, OSGi, custom repository,...

Then there were services which could be found via SPI, OSGi, custom repository,...

Then there were other artifacts like connections pools, schedulers,... 
The only thing they had in common is that they could be referred to by an id.

This API unifies that common trait and allows you to look up any of the above (and more) through a single point of entry.
This also makes it easy to customize that point of entry (e.g. a custom repository?)

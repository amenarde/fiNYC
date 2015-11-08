# fiNYC
A dynamic visualization interface of millions of taxi routes in New York City

Inspiration

Data is beautiful. We wanted to transform gigabytes of traffic data into a consumable and interactive visualize system to give users an intuitive and easy way to see trends in the data.

What it does

fiNYC analyzes over 11,000,000 nodes of New York taxi data to visualize all destinations from a given start position in a heat map overlaid on a map of New York.

How we built it

Our team implemented sorting and parsing algorithms in Java, and then used Swing libraries to overlay a homebrew heatmap on our map of NY.

Challenges we ran into

Optimizing our algorithms to run in lower order time was essential with such a large dataset. Additionally, aligning our heatmap (defined in pixels) perfectly with the map (defined by longitude and latitude) proved to be a fun challenge.

Accomplishments that we are proud of

Our Taxi cab logo is "the cutest thing [team member Ben] has ever seen"

What we learned

Standardizing data was a huge challenge. We learned the importance of picking the good data from the inconsistent, as well as good analytical techniques.

What's next for fiNYC

Integration with other taxi data, timestamp data, and fare data is the next step. We aim to bring users as rich and beautiful data visualization of their travel.

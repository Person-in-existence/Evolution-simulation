This is an evolution simulation (not very realistic) designed to demonstrate how evolution works. Random mutations are given to different factors (see factors file, or factors listed below), and the creatures can lose health and die for several reasons. This means that the population will slowly evolve over time.
Currently, I have no plans to upgrade this, but I may come back to it in the future. Right now, it is relatively slow - I reccomend not setting the amount of food too high, above around 100, as it can get quite laggy.
The factors are:

Creature factors:
Float Heat_loss - amount of heat lost per second.
Float Heat_gain - Peak amount of heat a creature can produce per second
INTERNAL Float heat - current heat the creature has. Influences the heat_loss variable - lower than ideal decreases heat loss, higher increases, to make this passively stable.
Integer red_comp - red colour value. the closer this is to the background equivalent, the more camouflaged it is
Integer green_comp - green colour value. the closer this is to the background equivalent, the more camouflaged it is
Integer blue_comp - blue colour value. the closer this is to the background equivalent, the more camouflaged it is
Float greed - amount more than required food it wants
Float stomach_size - amount of food it can hold. 1 unit of food is enough for one cycle, just like regular food. greed adds to the stomach size.
Float stomach_efficiency - Amount of input food that is usable in the stomach. Is modified in negative correlation by stomach size.
INTERNAL float stomach_fill - Amount of food currently in the stomach
Float reproduction_chance - Chance that another creature will choose it for reproduction. Multiple creatures can be chosen. If two creatures choose each other, they will reproduce. Negatively impacted on a per-creature basis
by how different they are in each statistic, hopefully.
Float ferocity - determines how much food this particular creature gets (total_ferocity/this.ferocity = percentage of food available). food received is capped by 1+greed.
INTERNAL Float health - 20 is maximum. Will regenerate by 1 for every extra bit of food there is (food available greater than 1). If food gets to 0, extra food required (food needed to get to 1) is removed from health
if heat gets to 0 or 50, 1 is removed from health per second.


Environment factors:
Float predatorChance - chance per second of a predator being able to see a Creature
Integer predatorSight - variation in any one of RGB from background required to be able to see the creature
Float predatorLuck - chance that, even if a creature is perfectly camouflaged, it gets eaten anyway
Float foodAmount - the number of creatures that can be fed by the food supply.
Float mutationChance - the chance that a mutation will occur in any given property.
Float mutationVariation - the min and max of the random function of the variation (min is the negative)
Float mutationRegression - thé amount the min and max gets adjusted towards the mean of the population.
Float passiveHeatGain - the amount of heat a creature gains passively from the environment
Integer red_comp - red value of the background
Integer green_comp - green value of the background
Integer blue_comp - blue value of the background

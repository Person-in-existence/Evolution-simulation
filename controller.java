import java.util.ArrayList;
import com.aparapi.Kernel;
import com.aparapi.Range;


public class controller{
    public controlPanel control;
    public Float predatorChance= 0.02F;
    public Integer predatorSight= 15;
    public Float predatorLuck = 0.05F;
    public Float foodAmount = 50F;
    public Float mutationChance = 0.1F;
    public Float mutationVariation = 0.5F;
    public Float mutationRegression = 0.3F;
    public Float passiveHeatGain = 1.5F;
    public Integer redComp = 50;
    public Integer greenComp = 50;
    public Integer blueComp = 50;
    public ArrayList<Creature> creatures = new ArrayList<>();
    public int iteration = 0;
    private boolean running = false;
    public int creatureCount = 0;
    public displayPanelController displayPanel;
    public Thread displayPanelThread;
    public Creature copiedSettings = new Creature();
    public void run() {
        control = new controlPanel();
        control.parent = this;
        Thread controlThread = new Thread(control);
        controlThread.start();
        displayPanel = new displayPanelController();
        displayPanel.parent = this;
        displayPanelThread = new Thread(displayPanel);
        displayPanelThread.start();
        int monthCounter = 0;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {}
            if (!running) {
                continue;
            }
            iteration++;
            monthCounter = (monthCounter + 1)%31;
            ArrayList<Creature> tempRemovalList = new ArrayList<>(creatures);
            for (Creature creature : tempRemovalList) {
                if (Main.randomChance(predatorChance)) {
                    if (Main.randomChance(Main.colourCutOff(creature.red, redComp, predatorSight))) {
                        creature.health = -10F;
                    } else if (Main.randomChance(Main.colourCutOff(creature.green, greenComp, predatorSight))) {
                        creature.health = -10F;
                    } else if (Main.randomChance(Main.colourCutOff(creature.blue, blueComp, predatorSight))) {
                        creature.health = -10F;
                    } else if (Main.randomChance(predatorLuck)) {
                        creature.health = -10F;
                    }
                }
            }

            Float total_ferocity = 0F;
            for (Creature creature : creatures) {
                total_ferocity += creature.ferocity;
            }
            ArrayList<Float> creatureFood = new ArrayList<>();
            ArrayList<Boolean> creatureGreed = new ArrayList<>();
            float spareFood = 0F;
            for (Creature creature : creatures) {
                Float potential = foodAmount * (creature.ferocity / total_ferocity);
                if (potential < creature.greed) {
                    creatureFood.add(potential);
                    creatureGreed.add(true);
                } else {
                    creatureFood.add(creature.greed);
                    creatureGreed.add(false);
                    spareFood += potential - creature.greed;
                }
            }
            for (int i = 0; i < creatures.size(); i++) {
                if (!(spareFood > 0)) {
                    break;
                }
                else {
                    float potential = creatures.get(i).greed - creatureFood.get(i);
                    if (creatureGreed.get(i)) {
                        if (spareFood >= potential) {
                            creatureFood.set(i, potential + creatureFood.get(i));
                            spareFood -= potential;
                        }
                    }
                }
            }
//            for (int i = 0; i < creatures.size(); i++) {
//                creatures.get(i).tick(creatureFood.get(i));
//            }

            // GPU CODE START
            assert (creatures.size() == creatureFood.size());
            Creature[] creaturesArray = new Creature[creatures.size()];
            float[] creatureFoodArray = new float[creatureFood.size()];
            for (int i = 0; i < creatures.size(); i++) {
                creaturesArray[i] = creatures.get(i);
                creatureFoodArray[i] = creatureFood.get(i);
            }

            Kernel kernel = new Kernel() {
                @Override
                public void run() {
                    int id = getGlobalId();
                    creaturesArray[id].tick(creatureFoodArray[id]);
                }
            };

            kernel.execute(creatures.size());
            kernel.dispose();


            // GPU CODE END

            // Reproduction
            ArrayList<Creature> tempList = new ArrayList<>(creatures);
            if (monthCounter % 10 == 0) {
                for (int i = 0; i < tempList.size(); i++) {
                    if (Main.randomChance(tempList.get(i).baseReproductionChance)) {
                        for (int y = 0; y < tempList.size(); y++) {
                            // Speciation Chance workout
                            int totalDifference = Main.difference(tempList.get(i).red, tempList.get(y).red) + Main.difference(tempList.get(i).green, tempList.get(y).green) + Main.difference(tempList.get(i).blue, tempList.get(y).blue);
                            float speciationMultiplier = Main.speciationMultiplier((float) totalDifference);
                            if (y!=i) {
                                if (Main.randomChance(tempList.get(i).baseReproductionChance*speciationMultiplier)) {
                                    reproduce(tempList.get(i), tempList.get(y));
                                    tempList.remove(tempList.get(i));
                                    break;
                                }
                            }
                            if (y == tempList.size()-1 & y != i & Main.randomChance(speciationMultiplier)) {
                                reproduce(tempList.get(i), tempList.get(y));
                                tempList.remove(tempList.get(i));
                            }
                        }
                    }
                }
            }
            if (control.cPanel != null) {
                control.cPanel.update(false);
            }
            displayPanel.update(creatures);
        }
    }
    public void reproduce(Creature creature1, Creature creature2) {
        Creature newCreature = new Creature();
        float averageHeatLoss = (creature1.heatLoss+creature2.heatLoss)/2;
        float averageHeatGain = (creature1.heatGain+creature2.heatGain)/2;
        int averageRed = (creature1.red+creature2.red)/2;
        int averageGreen = (creature1.green+creature2.green)/2;
        int averageBlue = (creature1.blue+creature2.blue)/2;
        float averageGreed = (creature1.greed+creature2.greed)/2;
        float averageStomachSize = (creature1.stomachSize+creature2.stomachSize)/2;
        float averageStomachEfficiency = (creature1.stomachEfficiency+creature2.stomachEfficiency)/2;
        float averageBaseReproductionChance = (creature1.baseReproductionChance+creature2.baseReproductionChance)/2;
        float averageFerocity = (creature1.ferocity+creature2.ferocity)/2;
        float mutationRegressionInverse = (1-mutationRegression);
        if (Main.randomChance(mutationChance)) {
            if (Main.randomChance(0.1F)) {
                averageHeatLoss += Main.randomFloat(mutationVariation) * mutationRegressionInverse;
            }
            if (Main.randomChance(0.1F)) {
                averageHeatGain += Main.randomFloat(mutationVariation)*mutationRegressionInverse;

            }
            if (Main.randomChance(0.1F)) {
                averageRed += Math.round(Main.randomFloat(mutationVariation)*10*mutationRegressionInverse);
            }
            if (Main.randomChance(0.1F)) {
                averageGreen += Math.round(Main.randomFloat(mutationVariation)*10*mutationRegressionInverse);
            }
            if (Main.randomChance(0.1F)) {
                averageBlue += Math.round(Main.randomFloat(mutationVariation)*10*mutationRegressionInverse);
            }
            if (Main.randomChance(0.1F)) {
                averageGreed += Main.randomFloat(mutationVariation)*mutationRegressionInverse;
            }
            if (Main.randomChance(0.1F)) {
                averageStomachSize += Main.randomFloat(mutationVariation)*mutationRegressionInverse;
            }
            if (Main.randomChance(0.1F)) {
                averageFerocity += Main.randomFloat(mutationVariation)*mutationRegressionInverse;
            }
            if (Main.randomChance(0.1F)) {
                averageStomachEfficiency = (float) Main.sigmoid(Main.randomFloat(mutationVariation)*mutationRegressionInverse+Main.unsigmoid(averageStomachEfficiency));
            }
            if (Main.randomChance(0.1F)) {
                averageBaseReproductionChance = (float) Main.sigmoid(Main.randomFloat(mutationVariation)*mutationRegressionInverse+Main.unsigmoid(averageBaseReproductionChance));
            }
        }

        newCreature.heatLoss = negativeCutoff(averageHeatLoss);
        newCreature.heatGain = negativeCutoff(averageHeatGain);
        newCreature.red = Math.min(negativeCutoff(averageRed), 255);
        newCreature.green = Math.min(negativeCutoff(averageGreen), 255);
        newCreature.blue = Math.min(negativeCutoff(averageBlue), 255);
        newCreature.greed = negativeCutoff(averageGreed);
        newCreature.stomachSize = negativeCutoff(averageStomachSize);
        newCreature.stomachFill = negativeCutoff(averageStomachSize);
        newCreature.stomachEfficiency = negativeCutoff(averageStomachEfficiency);
        newCreature.baseReproductionChance = negativeCutoff(averageBaseReproductionChance);
        newCreature.ferocity = negativeCutoff(averageFerocity);
        newCreature.control = this;
        newCreature.heat = 25F;
        creatureCount++;
        newCreature.name = "Creature #" + creatureCount;
        creatures.add(newCreature);
    }
    public void setPredatorChance(Float newPredatorChance) {predatorChance = newPredatorChance;}
    public void setPredatorSight(Integer newPredatorSight) {predatorSight = newPredatorSight;}
    public void setPredatorLuck(Float newPredatorLuck) {predatorLuck = newPredatorLuck;}
    public void setFoodAmount(Float newFoodAmount) {foodAmount=newFoodAmount;}
    public void setMutationChance(Float newMutationChance) {mutationChance = newMutationChance;}
    public void setMutationVariation(Float newMutationVariation) {mutationVariation = newMutationVariation;}
    public void setMutationRegression(Float newMutationRegression) {mutationRegression = newMutationRegression;}
    public void setPassiveHeatGain(Float newPassiveHeatGain) {passiveHeatGain = newPassiveHeatGain;}
    public void setRedComp(Integer newRedComp) {
        redComp = newRedComp;}
    public void setGreenComp(Integer newGreenComp) {greenComp = newGreenComp;}
    public void setBlueComp(Integer newBlueComp) {blueComp=newBlueComp;}
    public int getIteration() {return iteration;}
    public void setRunning(boolean newRunning) {running = newRunning;}
    public boolean getRunning() {return running;}
    public ArrayList<Creature> getCreatures() {return creatures;}
    public void addCreature(Creature creature) {
        creatures.add(creature);
    }
    public void kill(Creature creature) {
        creatures.remove(creature);
        if (control.cPanel != null) {
            control.cPanel.killCreature(creature);
        }
        System.out.println(creature + " Killed");
    }
    public static Float negativeCutoff(Float number) {
        if (number < 0) {
            return 0F;
        } else {
            return number;
        }
    }
    public static int negativeCutoff(int number) {
        return Math.max(number, 0);
    }

}

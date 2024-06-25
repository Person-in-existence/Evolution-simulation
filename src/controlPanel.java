public class controlPanel extends Thread{
    public controller parent;
    public environmentPanel ePanel;
    public creaturePanel cPanel;
    public Thread ePanelThread;
    public Thread cPanelThread;
    public void run() {
        ePanel = new environmentPanel();
        ePanel.parent = this;
        cPanel = new creaturePanel();
        cPanel.parent = this;
        ePanelThread = new Thread(ePanel);
        cPanelThread = new Thread(cPanel);
        ePanelThread.start();
        cPanelThread.start();
    }
    public void newCreaturePanel() {
        cPanel = new creaturePanel();
        cPanel.parent = this;
        cPanelThread = new Thread(cPanel);
        cPanelThread.start();
    }
    public void setPredatorChance(Float newPredatorChance) {parent.setPredatorChance(newPredatorChance);}
    public void setPredatorSight(Integer newPredatorSight) {parent.setPredatorSight(newPredatorSight);}
    public void setPredatorLuck(Float newPredatorLuck) {parent.setPredatorLuck(newPredatorLuck);}
    public void setFoodAmount(Float newFoodAmount) {parent.setFoodAmount(newFoodAmount);}
    public void setMutationChance(Float newMutationChance) {parent.setMutationChance(newMutationChance);}
    public void setMutationVariation(Float newMutationVariation) {parent.setMutationVariation(newMutationVariation);}
    public void setMutationRegression(Float newMutationRegression) {parent.setMutationRegression(newMutationRegression);}
    public void setPassiveHeatGain(Float newPassiveHeatGain) {parent.setPassiveHeatGain(newPassiveHeatGain);}
    public void setRedComp(Integer newRedComp) {parent.setRedComp(newRedComp);}
    public void setGreenComp(Integer newGreenComp) {parent.setGreenComp(newGreenComp);}
    public void setBlueComp(Integer newBlueComp) {parent.setBlueComp(newBlueComp);}
    public void setRunning(boolean newRunning) {parent.setRunning(newRunning);}
    public Float getPredatorChance() {return parent.predatorChance;}
    public Integer getPredatorSight() {return parent.predatorSight;}
    public Float getPredatorLuck() {return parent.predatorLuck;}
    public Float getFoodAmount() {return parent.foodAmount;}
    public Float getMutationChance() {return parent.mutationChance;}
    public Float getMutationVariation() {return parent.mutationVariation;}
    public Float getMutationRegression() {return parent.mutationRegression;}
    public Float getPassiveHeatGain() {return parent.passiveHeatGain;}
    public Integer getRedComp() {return parent.redComp;}
    public Integer getGreenComp() {return parent.greenComp;}
    public Integer getBlueComp() {return parent.blueComp;}
    public boolean getRunning() {return parent.getRunning();}
}

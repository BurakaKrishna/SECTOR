package de.datexis.rnn.loss;



import de.datexis.rnn.loss.MultiClassDosSantosPairwiseRankingLoss;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.nd4j.linalg.activations.IActivation;
import org.nd4j.linalg.activations.impl.ActivationTanH;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;


public class MultiClassDosSantosPairwiseRankingLossTest {

  private MultiClassDosSantosPairwiseRankingLoss rankingLoss = new MultiClassDosSantosPairwiseRankingLoss();

  @Test
  public void correctScoreResult() throws Exception {

    INDArray label = Nd4j.create(2, 3);
    label.put(0, 0, 1);
    label.put(0, 1, 0);
    label.put(0, 2, 0);
    label.put(1, 0, 1);
    label.put(1, 1, 0);
    label.put(1, 2, 0);

    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 4);
    preOutput.put(0, 1, 0);
    preOutput.put(0, 2, -1);
    preOutput.put(1, 0, 4);
    preOutput.put(1, 1, -1);
    preOutput.put(1, 2, 0);

    INDArray mask = Nd4j.ones(2, 1);

    IActivation tanH = new ActivationTanH();

    INDArray scoreArray = rankingLoss.scoreArray(label, preOutput, tanH, mask);

    double score = scoreArray.sumNumber().doubleValue();

    /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(3.05d, 0.05d)));
    assertThat(scoreArray.getDouble(0,1), is(closeTo(1.31d, 0.05d)));
    assertThat(scoreArray.getDouble(0,2), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,0), is(closeTo(3.05d, 0.05d)));
    assertThat(scoreArray.getDouble(1,1), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,2), is(closeTo(1.31d, 0.05d)));
  */

    assertThat(score, is(closeTo(8.726d, 0.05d)));
  }

  @Test
  public void correctScoreResultForMultiClass() throws Exception {

    INDArray label = Nd4j.create(2, 4);
    label.put(0, 0, 1);
    label.put(0, 1, 1);
    label.put(0, 2, 0);
    label.put(0, 3, 0);
    label.put(1, 0, 1);
    label.put(1, 1, 0);
    label.put(1, 2, 0);
    label.put(1, 3, 1);


    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 4);
    preOutput.put(0, 1, 4);
    preOutput.put(0, 2, 0);
    preOutput.put(0, 3, -1);
    preOutput.put(1, 0, 4);
    preOutput.put(1, 1, -1);
    preOutput.put(1, 2, 0);
    preOutput.put(1, 3, 4);


    INDArray mask = Nd4j.ones(2, 1);

    IActivation tanH = new ActivationTanH();

    INDArray scoreArray = rankingLoss.scoreArray(label, preOutput, tanH, mask);

    double score = scoreArray.sumNumber().doubleValue();

    /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(3.05d, 0.05d)));
    assertThat(scoreArray.getDouble(0,1), is(closeTo(1.31d, 0.05d)));
    assertThat(scoreArray.getDouble(0,2), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,0), is(closeTo(3.05d, 0.05d)));
    assertThat(scoreArray.getDouble(1,1), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,2), is(closeTo(1.31d, 0.05d)));
    */
    assertThat(score, is(closeTo(8.726d, 0.05d)));
  }

  @Test
  public void correctScoreResultForExampleWithoutLabel() throws Exception {

    INDArray label = Nd4j.create(3, 4);
    label.put(0, 0, 1);
    label.put(0, 1, 1);
    label.put(0, 2, 0);
    label.put(0, 3, 0);
    label.put(1, 0, 1);
    label.put(1, 1, 0);
    label.put(1, 2, 0);
    label.put(1, 3, 1);
    label.put(2, 0, 0);
    label.put(2, 1, 0);
    label.put(2, 2, 0);
    label.put(2, 3, 0);


    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 4);
    preOutput.put(0, 1, 4);
    preOutput.put(0, 2, 0);
    preOutput.put(0, 3, -1);
    preOutput.put(1, 0, 4);
    preOutput.put(1, 1, -1);
    preOutput.put(1, 2, 0);
    preOutput.put(1, 3, 4);
    preOutput.put(2, 0, 4);
    preOutput.put(2, 1, -1);
    preOutput.put(2, 2, 0);
    preOutput.put(2, 3, 4);


    INDArray mask = Nd4j.ones(3, 1);

    IActivation tanH = new ActivationTanH();

    INDArray scoreArray = rankingLoss.scoreArray(label, preOutput, tanH, mask);

    double score = scoreArray.sumNumber().doubleValue();

    /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(3.05d, 0.05d)));
    assertThat(scoreArray.getDouble(0,1), is(closeTo(1.31d, 0.05d)));
    assertThat(scoreArray.getDouble(0,2), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,0), is(closeTo(3.05d, 0.05d)));
    assertThat(scoreArray.getDouble(1,1), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,2), is(closeTo(1.31d, 0.05d)));
    */
    assertThat(score, is(closeTo(11.7735d, 0.05d)));
  }


  @Test
  public void correctDerivative() throws Exception {

    INDArray label = Nd4j.create(2, 3);
    label.put(0, 0, 1);
    label.put(0, 1, 0);
    label.put(0, 2, 0);
    label.put(1, 0, 1);
    label.put(1, 1, 0);
    label.put(1, 2, 0);

    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 1);
    preOutput.put(0, 1, 0);
    preOutput.put(0, 2, -2);
    preOutput.put(1, 0, 1);
    preOutput.put(1, 1, -2);
    preOutput.put(1, 2, 0);

    INDArray scoreArray = rankingLoss.computeDlDx(label, preOutput);

    double score = scoreArray.sumNumber().doubleValue();

    // LHS = 1
    // RHS = 1.46212
      /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(-1.9052d, 0.05d)));
    assertThat(scoreArray.getDouble(0,1), is(closeTo(1.4621d, 0.05d)));
    assertThat(scoreArray.getDouble(0,2), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,0), is(closeTo(-1.9052d, 0.05d)));
    assertThat(scoreArray.getDouble(1,1), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,2), is(closeTo(1.462, 0.05d)));
    */

    assertThat(score, is(closeTo(-0.8860, 0.05d)));
  }

  @Test
  public void correctDerivativeWithExampleWithoutLabel() throws Exception {

    INDArray label = Nd4j.create(3, 3);
    label.put(0, 0, 1);
    label.put(0, 1, 0);
    label.put(0, 2, 0);
    label.put(1, 0, 1);
    label.put(1, 1, 0);
    label.put(1, 2, 0);
    label.put(2, 0, 0);
    label.put(2, 1, 0);
    label.put(2, 2, 0);

    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 1);
    preOutput.put(0, 1, 0);
    preOutput.put(0, 2, -2);
    preOutput.put(1, 0, 1);
    preOutput.put(1, 1, -2);
    preOutput.put(1, 2, 0);
    preOutput.put(2, 0, 1);
    preOutput.put(2, 1, -2);
    preOutput.put(2, 2, 0);

    INDArray scoreArray = rankingLoss.computeDlDx(label, preOutput);

    double score = scoreArray.sumNumber().doubleValue();

    // LHS = 1
    // RHS = 1.46212

    assertThat(scoreArray.getDouble(0, 0), is(closeTo(-1.9052d, 0.05d)));
    assertThat(scoreArray.getDouble(0, 1), is(closeTo(1.4621d, 0.05d)));
    assertThat(scoreArray.getDouble(0, 2), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1, 0), is(closeTo(-1.9052d, 0.05d)));
    assertThat(scoreArray.getDouble(1, 1), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1, 2), is(closeTo(1.462, 0.05d)));
    assertThat(scoreArray.getDouble(2, 0), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(2, 1), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(2, 2), is(closeTo(0d, 0.0005d)));


    assertThat(score, is(closeTo(-0.8860, 0.05d)));
  }

  @Test
  public void correctDerivativeWithMultipleCorrectLabels() throws Exception {

    INDArray label = Nd4j.create(3, 3);
    label.put(0, 0, 1);
    label.put(0, 1, 0);
    label.put(0, 2, 0);
    label.put(1, 0, 1);
    label.put(1, 1, 0);
    label.put(1, 2, 0);
    label.put(2, 0, 1);
    label.put(2, 1, 1);
    label.put(2, 2, 0);

    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 1);
    preOutput.put(0, 1, 0);
    preOutput.put(0, 2, -2);
    preOutput.put(1, 0, 1);
    preOutput.put(1, 1, -2);
    preOutput.put(1, 2, 0);
    preOutput.put(2, 0, 1);
    preOutput.put(2, 1, -2);
    preOutput.put(2, 2, 0);

    INDArray scoreArray = rankingLoss.computeDlDx(label, preOutput);

    double score = scoreArray.sumNumber().doubleValue();

    // LHS = 1
    // RHS = 1.46212
      /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(-1.9052d, 0.05d)));
    assertThat(scoreArray.getDouble(0,1), is(closeTo(1.4621d, 0.05d)));
    assertThat(scoreArray.getDouble(0,2), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,0), is(closeTo(-1.9052d, 0.05d)));
    assertThat(scoreArray.getDouble(1,1), is(closeTo(0d, 0.0005d)));
    assertThat(scoreArray.getDouble(1,2), is(closeTo(1.462, 0.05d)));
    */

    assertThat(score, is(closeTo(-1.3653d, 0.05d)));
  }

  @Test
  public void correctScoreResultForOneSample() throws Exception {

    INDArray label = Nd4j.create(1, 2);
    label.put(0, 0, 1);
    label.put(0, 1, 0);


    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 4);
    preOutput.put(0, 1, 0);


    INDArray mask = Nd4j.ones(1, 1);

    IActivation tanH = new ActivationTanH();

    INDArray scoreArray = rankingLoss.scoreArray(label, preOutput, tanH, mask);

    double score = scoreArray.sumNumber().doubleValue();
  
    /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(3.05d, 0.05d)));
    assertThat(scoreArray.getDouble(0,1), is(closeTo(1.31d, 0.05d)));
    */
    assertThat(score, is(closeTo(4.362d, 0.05d)));
  }

  @Test
  public void correctScoreResultForBadPerformingSample() throws Exception {

    INDArray label = Nd4j.create(1, 2);
    label.put(0, 0, 1);
    label.put(0, 1, 0);


    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 0);
    preOutput.put(0, 1, 4);


    INDArray mask = Nd4j.ones(1, 1);

    IActivation tanH = new ActivationTanH();

    INDArray scoreArray = rankingLoss.scoreArray(label, preOutput, tanH, mask);

    double score = scoreArray.sumNumber().doubleValue();
    
    /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(5.01d, 0.05d)));
    assertThat(scoreArray.getDouble(0,1), is(closeTo(3.05d, 0.05d)));
    */
    assertThat(score, is(closeTo(8.055d, 0.05d)));
  }

  @Test
  public void correctScoreResultUseNegativeSampleWithHighestScoreForRightHandLog() throws Exception {
    INDArray label = Nd4j.create(2, 4);
    label.put(0, 0, 1);
    label.put(0, 1, 0);
    label.put(0, 2, 0);
    label.put(0, 3, 0);
    label.put(1, 0, 1);
    label.put(1, 1, 0);
    label.put(1, 2, 0);
    label.put(1, 3, 0);

    INDArray preOutput = Nd4j.create(label.shape());
    preOutput.put(0, 0, 0);
    preOutput.put(0, 1, 0.2);
    preOutput.put(0, 2, 0.5);
    preOutput.put(0, 3, 0.1);
    preOutput.put(1, 0, 0);
    preOutput.put(1, 1, 0.2);
    preOutput.put(1, 2, 0.4);
    preOutput.put(1, 3, 0.5);

    INDArray mask = Nd4j.ones(2, 1);

    IActivation tanH = new ActivationTanH();

    INDArray scoreArray = rankingLoss.scoreArray(label, preOutput, tanH, mask);

    double score = scoreArray.sumNumber().doubleValue();

    /*
    assertThat(scoreArray.getDouble(0,0), is(closeTo(5.01d, 0.05d)));
    assertThat(scoreArray.getDouble(1,0), is(closeTo(5.01d, 0.05d)));
    */
    assertThat(score, is(closeTo(14.134d, 0.05d)));
  }

}
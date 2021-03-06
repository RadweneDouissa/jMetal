//  ZDT1.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.problem.multiobjective.glt;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem GLT3. Defined in
 * F. Gu, H.-L. Liu, and K. C. Tan, “A multiobjective evolutionary
 * algorithm using dynamic weight design method,” International Journal
 * of Innovative Computing, Information and Control, vol. 8, no. 5B, pp.
 * 3677–3688, 2012.
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class GLT3 extends AbstractDoubleProblem {

  /**
   * Default constructor
   */
  public GLT3() {
    this(10) ;
  }

  /**
   * Constructor
   * @param numberOfVariables
   */
  public GLT3(int numberOfVariables) {
    setNumberOfVariables(numberOfVariables);
    setNumberOfObjectives(2);
    setName("GLT3");

    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

    lowerLimit.add(0.0) ;
    upperLimit.add(1.0) ;
    for (int i = 1; i < getNumberOfVariables(); i++) {
      lowerLimit.add(-1.0);
      upperLimit.add(1.0);
    }

    setLowerLimit(lowerLimit);
    setUpperLimit(upperLimit);
  }

  @Override
  public void evaluate(DoubleSolution solution) {
    solution.setObjective(0, (1.0 + g(solution))*solution.getVariableValue(0));
    if (solution.getObjective(0) < 0.05) {
      solution.setObjective(1, (1.0 + g(solution))*(1.0 - 19.0*solution.getVariableValue(0))) ;
    } else {
      solution.setObjective(1, (1.0 + g(solution))*(1.0/19.0 - solution.getVariableValue(0)/19.0)) ;
    }
  }

  private double g(DoubleSolution solution) {
    double result = 0.0 ;

    for (int i = 1; i < solution.getNumberOfVariables(); i++) {
      double value =solution.getVariableValue(i)
          - Math.sin(2*Math.PI*solution.getVariableValue(0)+i*Math.PI/solution.getNumberOfVariables()) ;

      result += value * value ;
    }

    return result ;
  }
}

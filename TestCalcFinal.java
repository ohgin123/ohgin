package com.idealgen.solution.calculator;
import java.util.ArrayList;
import java.util.List;

import java.lang.Double;
import java.lang.Character;

public class TestCalcFinal {

	public static void main(String[] args) {
		System.out.println(Float.valueOf("23") - Float.valueOf("16.8"));
		System.out.println(calculate("23 - 16.8"));
		System.out.println(calculate("1 + 1"));
		System.out.println(calculate("2 * 2"));
		System.out.println(calculate("1 + 2 + 3"));
		System.out.println(calculate("6/2"));
		System.out.println(calculate("11+23"));
		System.out.println(calculate("11.1 + 23"));
		System.out.println(calculate("1+1*3"));
		System.out.println(calculate("(11 + 11)  / (11 + 11) "));
		System.out.println(calculate("(11.5+15.4)+10.1"));
		System.out.println(calculate("23 - ( 29.3 - 12.5 )"));
		System.out.println(calculate("23 - ( 29.3 - 12.5 )"));
	}
	
	public static double calculate(String sum) {
		sum = sum.replace(" ",""); // Remove white spaces
		ArrayList<Double> listDouble = new ArrayList<Double>(); //Store Double values
		ArrayList<Character> listCharacter = new ArrayList<Character>(); // Store Character Values
		
		
		/*
		 * Adding token to either Double ArrayList or Character ArrayList
		 * 
		 */
		String tempDouble = "";
		for (Character ch:sum.toCharArray()) {
			if (Character.isDigit(ch) || ch.equals('.')) {
				tempDouble = tempDouble + ch;
			} else {
				if (!tempDouble.equals("")) {
					
					listDouble.add(Double.valueOf(tempDouble));
					tempDouble="";
				}
				listCharacter.add(ch);
			}
		}
		if (!tempDouble.equals("")) {
			listDouble.add(Double.valueOf(tempDouble));
		}
		
		
		
		int listCharacterCounter = 0;
		int listDoubleCounter = 0;
		
		Double returnValue = 0.0;
		Double processedValue1 = 0.0;
		Double processedValue2 = 0.0;
		Character chValue2 = ' ';
		
		boolean firstProcessed = false;
		
		while (true) {
			if (listCharacter.get(listCharacterCounter) == '(') {
				listCharacterCounter++;
				ArrayList<Object> retrievalList = (ArrayList<Object>)performBracketOperation(listDouble,
						listCharacter, listDoubleCounter, listCharacterCounter);
				processedValue1 = (Double)retrievalList.get(0);
				listDoubleCounter = (Integer)retrievalList.get(1);
				listCharacterCounter = (Integer) retrievalList.get(2);
			} else {
				if (!firstProcessed) {
					firstProcessed = true;
					processedValue1 = listDouble.get(listDoubleCounter);
					listDoubleCounter++;
				} else {
					processedValue1 = returnValue;
				}
			}
			
			
			if (listCharacterCounter < listCharacter.size()) {
				chValue2 = listCharacter.get(listCharacterCounter);
				listCharacterCounter++;
				if (listCharacterCounter < listCharacter.size() && listCharacter.get(listCharacterCounter) == '(') {
					listCharacterCounter++;
					ArrayList<Object> retrievalList = (ArrayList<Object>)performBracketOperation(listDouble,
							listCharacter, listDoubleCounter, listCharacterCounter);
					processedValue2 = (Double)retrievalList.get(0);
					listDoubleCounter = (Integer)retrievalList.get(1);
					listCharacterCounter = (Integer) retrievalList.get(2);
				}  else {
					processedValue2 = listDouble.get(listDoubleCounter);
					listDoubleCounter++;
				}
				returnValue = performOperation(processedValue1,processedValue2, chValue2);
			}
			if (listDoubleCounter > (listDouble.size() - 1)) {
				break;
			}
		}
		return returnValue;
	}
	
	private static List<Object> performBracketOperation(List<Double> listDouble, List<Character> listCharacter
				, int listDoubleCounter, int listCharacterCounter ) {
		boolean loopProcessed=false;
		Double doubleValue = 0.0;
		Double doubleValue2 = 0.0;
		Double finalValue = 0.0;
		Character chValue = ' ';
		Double processedValue1 = 0.0;
		List<Object> returnList = new ArrayList<Object>();
		
		while (true) {
			if (!loopProcessed) {
				loopProcessed = true;
				doubleValue = listDouble.get(listDoubleCounter);
				listDoubleCounter++;
			} else {
				doubleValue = finalValue;
			}
			chValue = listCharacter.get(listCharacterCounter);
			listCharacterCounter++;
			doubleValue2 = listDouble.get(listDoubleCounter);
			listDoubleCounter++;
			finalValue = performOperation(doubleValue,doubleValue2, chValue);
			if (listCharacter.get(listCharacterCounter) == ')') {
				listCharacterCounter++;
				processedValue1 = finalValue;
				break;
			}
		}
		
		returnList.add(processedValue1);
		returnList.add(listDoubleCounter);
		returnList.add(listCharacterCounter);
		
		return returnList;
		
	}
	
	private static double performOperation(Double doubleValue1, Double doubleValue2, Character chValue) {
		if (chValue == '+') {
			return doubleValue1 + doubleValue2;
		} else if (chValue == '-') {
			return doubleValue1 - doubleValue2;
		} else if (chValue == '*') {
			return doubleValue1 * doubleValue2;
		} else if (chValue == '/') {
			return doubleValue1 / doubleValue2;
		}
		return 0;
	}

}

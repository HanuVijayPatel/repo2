package com.vijay.anagram;

import java.util.List;
import java.util.Scanner;

import com.vijay.anagram.utility.Dictionary;

public class AnagramApp {

	public static void main(String[] args) {

		// Load the dictionary file in to list

		String fileName = "C:\\Temp\\words.txt";
		if(args != null && args.length > 0) {
			fileName = args[0];
		}
		Dictionary dict = new Dictionary();

		try {
			dict.setDictionaryFileName(fileName);
			dict.loadFile();

			Scanner scanner = new Scanner(System.in);
			String input = "" ;
			while(!input.equalsIgnoreCase("exit")) {


				System.out.print("Input the String (type '[exit|EXIT]' to stop):  ");
				input = scanner. nextLine();

				System.out.println("You have input String :  " + input  + " , try to find anagram words..");
				List<String> anagList = dict.getListOfAnagram(input);
				System.out.println("List of Anagram Word -> " + anagList);

			}
			scanner.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("Program Exited... ");

	}

}

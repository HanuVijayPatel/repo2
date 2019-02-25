package com.vijay.anagram.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Dictionary {

	@Value("${spring.dictionary.filename}")
	public String dictionaryFileName;

	private Set<String> dictionary;
	private Map<String, List<String>> anagramMap = Collections.synchronizedMap(new HashMap<String, List<String>>());

	public Dictionary() {
	}

	@PostConstruct
	public void loadFile() {

		try {
			System.out.println("Loading dictionary file... ");
			dictionary = new HashSet<String>();
			try (Scanner scan = new Scanner(new File(dictionaryFileName))) {
				while (scan.hasNext()) {
					dictionary.add(scan.nextLine());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Loading dictionary file...Completed .. ");
			
		// build the anagram map
		buildAnagramMap();
	}


	private void buildAnagramMap() {
		
		// create the map of anagram strings from dictionary 
		for (String str : dictionary) {
			char[] temp = str.toCharArray();
			Arrays.sort(temp);
			String key = new String(temp).toLowerCase();
			if (anagramMap.get(key) != null) {
				anagramMap.get(key).add(str);//.toLowerCase());
			} else {
				List<String> anagramList = new ArrayList<>();
				anagramList.add(str);
				anagramMap.put(key, anagramList);
			}
		}
	}
	
	public void setDictionaryFileName(String dictionaryFileName) {
		this.dictionaryFileName = dictionaryFileName;
	}


	public List<String> getListOfAnagram(String word) {

		List<String> retrunAnagramList;
		char[] key = word.toCharArray();
		Arrays.sort(key);
		word = new String(key).toLowerCase();
		if (anagramMap.containsKey(word)) {
			retrunAnagramList = anagramMap.get(word);
		} else {
			retrunAnagramList = Collections.emptyList();
		}

		return retrunAnagramList;
	}
	
	
	public boolean addWord(String s) {
		
		if(dictionary.add(s)) {
			// add in anagram map
			char[] key = s.toCharArray();
			Arrays.sort(key);
			String tlcKey = new String(key).toLowerCase();
			if (anagramMap.containsKey(tlcKey)) {
				anagramMap.get(tlcKey).add(s);
			} else {
				// add new word in map
				List<String> anagramList = new ArrayList<>();
				anagramList.add(s);
				anagramMap.put(tlcKey, anagramList);
			}
			return true;
		}
		return false;
	}

	public boolean deleteWord(String s) {
		
		// remove from dictionary..
		if(dictionary.remove(s)) {
			
			// remove from the anagram map
			char[] key = s.toCharArray();
			Arrays.sort(key);
			String tlcKey = new String(key).toLowerCase();
			if (anagramMap.containsKey(tlcKey)) {
				anagramMap.get(tlcKey).remove(s);
			}
			return true;
		}
		return false;
	}
}

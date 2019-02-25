package com.vijay.anagram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.anagram.utility.Dictionary;

@RestController
public class AnargramController {
	
	@Autowired
	Dictionary dict;

	@RequestMapping("/anargram")
	public String welcomeUser(@RequestParam(name="name", required=false, defaultValue="Default") String name) {
		return "WelCome to Anagram - " + name;
	}
	
	@RequestMapping("/anargram/get")
	public String listAngram(@RequestParam(name="word", required=true) String word) {
		return "List of Anargram words - " + dict.getListOfAnagram(word);
	}
	
	@RequestMapping("/anargram/add")
	public String addWord(@RequestParam(name="word", required=true ) String newWord) {
		if(dict.addWord(newWord))
		   return "Word added - " + newWord;
		else
			return "Word already exist -  " + newWord;
	}
	
	@RequestMapping("/anargram/delete")
	public String deleteWord(@RequestParam(name="word", required=true ) String word) {
		if(dict.deleteWord(word))
		      return "Word deleted..." + word;
		else
			return "Word can not be deleted." + word;
	}
}

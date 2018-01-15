package Zemberek.yazlab.proje;


import java.util.ArrayList;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import org.json.JSONObject;

import zemberek.core.Histogram;

public class ZemberekIslemler 
{
	private static final Zemberek zemberek = new Zemberek(new TurkiyeTurkcesi());

	public static String parsePunctuations(String k)
	{
		k = k.replaceAll("[^a-zA-ZğşıçüöĞÖÇŞÜİ]", " ").replaceAll("\\s+", " ");
		return k;
	}

	public static String Process(String sentence) 
	{
		
		// noktalama isaretleri ayıklanıyor
		String input = parsePunctuations(sentence);
		ArrayList<String> wordsList = new ArrayList<String>();
		String[] stopwords = { "acaba", "altmış", "altı", "	ama", "ancak",
				"artık", "asla", "aslında", "az", "bana", "bazen", "bazıları",
				"bazı", "bazısı", "belki", "ben", "bile", "benden", "beni",
				"benim", "beş", "bin", "bir", "birçoğu", "biri", "birkaç",
				"birkez", "birşey", "birçok", "birçokları", "birisi",
				"birşeyi", "biz", "bizden", "bizi", "bizim", "bu", "buna",
				"bunda", "bundan", "bunu", "birkaçı", "bize", "böyle",
				"böylece", "burada", "bütün", "bunun", "çoğu", "çoğuna",
				"çoğunu", "çok", "çünkü", "da", "daha", "dahi", "de", "defa",
				"diye", "doksan", "dokuz", "dört", "elli", "değil", "diğer",
				"diğeri", "diğerleri", "dolayı", "en", "gibi", "hem", "hep",
				"hepsi", "her", "hiç", "iki", "ile", "ise", "için",
				"katrilyon", "kez", "ki", "kim", "kimden", "kime", "kimi",
				"kırk", "milyar", "milyon", "mu", "mü", "mi", "nasıl", "ne",
				"neden", "nerde", "nerede", "nereye", "niye", "niçin", "on",
				"ona", "ondan", "onlar", "onlardan", "onları", "onların",
				"onu", "otuz", "sanki", "sekiz", "seksen", "sen", "senden",
				"seni", "senin", "siz", "sizden", "sizi", "sizin", "trilyon",
				"tüm", "ve", "veya", "ya", "yani", "yedi", "yetmiş", "yirmi",
				"yüz", "üç", "şey", "şeyden", "şeyi", "şeyler", "şu", "bu",
				"buna", "bunda", "bundan", "bunu" };
		Histogram<String> histogram = new Histogram<>();
		JSONObject mtFive = new JSONObject();
		JSONObject frequency = new JSONObject();
		JSONObject mainObj = new JSONObject();
		
		try 
		{

			String nonStopWord = "";
			String rootVersion = "";

			StringBuilder builder = new StringBuilder(input);
			String[] words = builder.toString().split("\\s");

			for (String word : words) 
			{
				wordsList.add(word);
			}

			for (int i = 0; i < wordsList.size(); i++) 
			{
				for (int j = 0; j < stopwords.length; j++) 
				{
					if (stopwords[j].contains(wordsList.get(i))) 
					{
						wordsList.remove(i);
					}
				}
			}

			for (int i = 0; i < wordsList.size(); i++)
			{
				nonStopWord += wordsList.get(i) + " ";

			}

			for (int i = 0; i < wordsList.size(); i++)
			{
				rootVersion += zemberek.kelimeCozumle(wordsList.get(i))[0]
						.kok().icerik() + " ";
			}

			for (String str : wordsList)
			{
				histogram.add(str);
			}

			for (String s : histogram.getSortedList())
			{
				// Frekans değerleri hesaplanıyor
				frequency.put(s, histogram.getCount(s));
			}

			// 5den fazlaları listeler
			histogram.removeSmaller(5);

			for (String s : histogram.getSortedList()) 
			{
				mtFive.put(s, histogram.getCount(s));
			}

			mainObj.put("frequency", frequency);
			mainObj.put("root_version", rootVersion);
			mainObj.put("non_stopword", nonStopWord);
			mainObj.put("more_then_five", mtFive);

		} catch (Exception ex) {
			return ex.toString();
		}

		return mainObj.toString();
	}

}

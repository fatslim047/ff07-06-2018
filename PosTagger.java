package stanford_1;

import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PosTagger {
	MaxentTagger tagger;

	public PosTagger() {
		tagger = new MaxentTagger("/home/emily/workspace/Extraction_2/english-left3words-distsim.tagger");
	}

	public String tagString(String textToTag) {
		textToTag = textToTag.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
		return tagger.tagString(textToTag);
	}

	public String convertToSimpleTags(String taggedString) {
		String[] tokens = taggedString.split("\\s+");
		StringBuffer simpleTaggedString = new StringBuffer();
		for (int i=0;i<tokens.length;i++) {
			String token = tokens[i];
			String word = token.substring(0, token.lastIndexOf('_'));
			String pos = token.substring(token.lastIndexOf('_') + 1);
			simpleTaggedString.append(word + "#");
			if ("NN".equals(pos) || "NNS".equals(pos) || "NNP".equals(pos)
					|| "NNPS".equals(pos))
				simpleTaggedString.append("n");
			else if ("JJ".equals(pos) || "JJR".equals(pos) || "JJS".equals(pos))
				simpleTaggedString.append("a");
			else if ("RB".equals(pos) || "RBR".equals(pos) || "RBS".equals(pos)
					|| "WRB".equals(pos))
				simpleTaggedString.append("r");
			else if ("VB".equals(pos) || "VBD".equals(pos) || "VBG".equals(pos)
					|| "VBN".equals(pos) || "VBP".equals(pos)
					|| "VBZ".equals(pos))
				simpleTaggedString.append("v");
			else if ("PRP".equals(pos) || "PRP$".equals(pos)
					|| "WP".equals(pos) || "WP$".equals(pos))
				simpleTaggedString.append("a");
			if(i<tokens.length-1) simpleTaggedString.append(" ");
			//System.out.println(pos);
		}
		return simpleTaggedString.toString();
	}

	/*public static void main(String... s) throws IOException {
		PosTagger ptdemo = new PosTagger();
		String original = "what a smartphone !, it 's fantastic #thephoneofthecentury #lovely @samsung ,,, 1232,15 ";
		System.out.println("original:\n" + original);
		String taggedString = ptdemo.tagString(original);
		System.out.println("tagged:\n" + taggedString);
		String simpleString = ptdemo.convertToSimpleTags(taggedString);
		System.out.println("simple-tagged:\n" + simpleString);
	}*/

}
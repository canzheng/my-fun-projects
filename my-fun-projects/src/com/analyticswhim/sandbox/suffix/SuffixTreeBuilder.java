package com.analyticswhim.sandbox.suffix;

import java.util.Iterator;
import java.util.List;

public class SuffixTreeBuilder {
	public static final char DELIMITER = '$';

	public TreeNode buildSuffixTree(String s_) {
		String padded = s_ + DELIMITER;
		char[] str = padded.toCharArray();

		TreeNode head = new TreeNode(-1);
		for (int pos = 0; pos < str.length - 1; ++pos) {
			System.out.println("Phase: " + pos + ", adding suffix: "
					+ s_.substring(pos));
			// build suffix tree in phases.
			// each phase starts from advancing current position in string by 1
			// char
			TreeNode currentNode = head;
			int currentPos = pos;
			while (true) {
				// this loop searches from head to leaf via matching edges
				TreeNode matchNode = null;
				Iterator<TreeNode> iter = currentNode._childList.iterator();
				while (iter.hasNext()) {
					TreeNode child = iter.next();
					if (str[child._activePos] == str[currentPos]) {
						matchNode = child;
						break;
					}
				}
				if (matchNode != null) {
					// path matched, keep progressing until activeEnd
					System.out.println("First char matched: "
							+ str[matchNode._activePos]);

					int fwdChars = 0;
					while (str[matchNode._activePos + fwdChars] == str[currentPos
							+ fwdChars]
							&& matchNode._activePos + fwdChars < matchNode._activeEnd) {
						fwdChars++;
					}

					if (matchNode._activePos + fwdChars == matchNode._activeEnd) {
						// complete match of path
						// follow edge to next level;
						System.out.println("Full path matched: "
								+ padded.substring(matchNode._activePos,
										matchNode._activeEnd));
						currentNode = matchNode;
						currentPos = currentPos + fwdChars;
					} else {
						// partial match of path
						// split currentNode;
						System.out.println("Patial path matched: "
								+ padded.substring(matchNode._activePos,
										matchNode._activePos + fwdChars));
						TreeNode branching = new TreeNode(matchNode._activePos,
								matchNode._activePos + fwdChars);
						matchNode._activePos = matchNode._activePos + fwdChars;
						branching._childList.add(matchNode);
						branching._childList.add(new TreeNode(currentPos + fwdChars));
						iter.remove();
						currentNode._childList.add(branching);
						break;
					}
				} else {
					// no match, direct adding node to currentNode
					currentNode._childList.add(new TreeNode(currentPos));
					break;
				}
			}

		}
		return head;
	}

	public static void main(String[] args_) {
		String s = "ababbabbaabbabb";
		TreeNode head = new SuffixTreeBuilder().buildSuffixTree(s + DELIMITER);
		head.printPartialTreeReadable(s + DELIMITER);
	}
}

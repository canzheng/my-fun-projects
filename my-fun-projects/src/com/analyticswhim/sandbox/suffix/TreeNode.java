package com.analyticswhim.sandbox.suffix;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TreeNode {
	public TreeNode _next;
	public List<TreeNode> _childList = new LinkedList<TreeNode>();
	public TreeNode _parent;
	public int _activePos;
	public int _activeEnd;

	public TreeNode(int activePos_, int activeEnd_) {
		_activePos = activePos_;
		_activeEnd = activeEnd_;
	}

	public TreeNode(int activePos_) {
		this(activePos_, Integer.MAX_VALUE);
	}

	public void printPartialTree() {
		printPartialTree(0);
	}
	public void printPartialTreeReadable(String str_) {
		printPartialTreeReadable(0, str_);
	}

	private void printPartialTreeReadable(int indent_, String str_) {
		String nodeStr = "- HEAD -";
		if (_activePos != -1) {
			nodeStr = "Node: "
					+ (_activeEnd == Integer.MAX_VALUE ? str_.substring(_activePos) : str_.substring(_activePos, _activeEnd));
		}
		System.out.println(StringUtils.repeat(' ', indent_) + nodeStr);
		for (TreeNode child : _childList) {
			child.printPartialTreeReadable(indent_ + 4, str_);
		}
		
	}

	private void printPartialTree(int indent_) {

		System.out.println(StringUtils.repeat(' ', indent_) + "Node {"
				+ _activePos + ", " + _activeEnd + "}");
		for (TreeNode child : _childList) {
			child.printPartialTree(indent_ + 4);
		}
	}
}

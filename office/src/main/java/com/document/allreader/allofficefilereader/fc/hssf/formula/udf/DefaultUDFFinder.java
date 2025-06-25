

package com.document.allreader.allofficefilereader.fc.hssf.formula.udf;

import java.util.HashMap;
import java.util.Map;

import com.document.allreader.allofficefilereader.fc.hssf.formula.function.FreeRefFunction;


/**
 * Default UDF finder - for adding your own user defined functions.
 *
 * @author PUdalau
 */
public final class DefaultUDFFinder implements UDFFinder {
	private final Map<String, FreeRefFunction> _functionsByName;

	public DefaultUDFFinder(String[] functionNames, FreeRefFunction[] functionImpls) {
		int nFuncs = functionNames.length;
		if (functionImpls.length != nFuncs) {
			throw new IllegalArgumentException(
					"Mismatch in number of function names and implementations");
		}
		HashMap<String, FreeRefFunction> m = new HashMap<String, FreeRefFunction>(nFuncs * 3 / 2);
		for (int i = 0; i < functionImpls.length; i++) {
			m.put(functionNames[i].toUpperCase(), functionImpls[i]);
		}
		_functionsByName = m;
	}

	public FreeRefFunction findFunction(String name) {
		return _functionsByName.get(name.toUpperCase());
	}
}

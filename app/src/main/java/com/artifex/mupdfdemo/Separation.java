package com.artifex.mupdfdemo;

public class Separation
{
	public int rgba;
	public int cmyk;
	public String name;

	public Separation(String name, int rgba, int cmyk)
	{
		this.name = name;
		this.rgba = rgba;
		this.cmyk = cmyk;
	}
}


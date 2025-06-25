package com.document.allreader.allofficefilereader.fc.doc;

import java.util.ArrayList;
import java.util.List;

import com.document.allreader.allofficefilereader.common.autoshape.pathbuilder.ArrowPathAndTail;
import com.document.allreader.allofficefilereader.common.autoshape.pathbuilder.LineArrowPathBuilder;
import com.document.allreader.allofficefilereader.common.shape.WPAutoShape;

import android.graphics.Path;
import android.graphics.PointF;


public class VMLPathParser 
{
	public final static byte Command_Invalid = -1;
	
	public final static byte Command_MoveTo = 0;
	
	public final static byte Command_LineTo = Command_MoveTo + 1;
	
	public final static byte Command_CurveTo = Command_LineTo + 1;
	
	public final static byte Command_Close = Command_CurveTo + 1;
	
	public final static byte Command_End = Command_Close + 1;
	
	public final static byte Command_RMoveTo = Command_End + 1;
	
	public final static byte Command_RLineTo = Command_RMoveTo + 1;
	
	public final static byte Command_RCurveTo = Command_RLineTo + 1;
	
	public final static byte Command_NoFill = Command_RCurveTo + 1;
	
	public final static byte Command_NoStroke = Command_NoFill + 1;
	
	public final static byte Command_AngleEllipseTo = Command_NoStroke + 1;
	
	public final static byte Command_AngleEllipse = Command_AngleEllipseTo + 1;
	
	public final static byte Command_ArcTo = Command_AngleEllipse + 1;
	
	public final static byte Command_Arc = Command_ArcTo +1;
	
	public final static byte Command_ClockwiseArcTo = Command_Arc + 1;
	
	public final static byte Command_ClockwiseArc = Command_ClockwiseArcTo + 1;
	
	private final static byte Command_EllipticalQaudrantX = Command_ClockwiseArc + 1;
	
	private final static byte Command_EllipticalQaudrantY = Command_EllipticalQaudrantX + 1;
	
	private final static byte Command_QuadraticBezier = Command_EllipticalQaudrantY + 1;
	
	private static VMLPathParser instance = new VMLPathParser();
	
	private static byte NodeType_Invalidate = -1;
	private static byte NodeType_Start = 0;
	private static byte NodeType_Middle = 1;
	private static byte NodeType_End = 2;
	
	private byte currentNodeType = NodeType_Invalidate;
	private byte preNodeType = NodeType_Invalidate;
	
	private PointF preNode = new PointF();
	private PointF ctrNode1 = new PointF();
	private PointF ctrNode2 = new PointF();
	private PointF nextNode = new PointF();
	
	Path startArrowPath = null;
	Path endArrowPath = null;
	
	private VMLPathParser()
	{		
	}
	
	public static VMLPathParser instance()
	{
		return instance;
	}
	
	public PathWithArrow createPath(WPAutoShape autoshape, String pathContext, int lineWidth)
	{
		try
		{
			index = 0;
			startArrowPath = null;
			endArrowPath = null;
			
			List<Path> pathList = new ArrayList<Path>();
			Path path= null;
			boolean newPath = true;
			byte command = nextCommand(pathContext);
			byte nextCommand = command;
			currentNodeType = NodeType_Start;
			preNodeType = NodeType_Invalidate;
					
			while(command != Command_Invalid)
			{
				if(command == Command_End)
				{
					newPath = true;
					nextCommand = nextCommand(pathContext);
					if(nextCommand == Command_Invalid)
					{
						currentNodeType = NodeType_End;
					}
				}
				else
				{
					if(newPath)
					{
						newPath = false;
						path = new Path();
						pathList.add(path);						
					}
					
					Integer[] paras = nextParameters(pathContext);
					
					nextCommand = nextCommand(pathContext);
					if(nextCommand == Command_Invalid || nextCommand == Command_End)
					{
						currentNodeType = NodeType_End;
					}
					
					processPath(autoshape, lineWidth, path, command, paras);
					
					preNodeType = currentNodeType;
					currentNodeType = NodeType_Middle;
				}
				
				command = nextCommand;
			}
			
			PathWithArrow pathWithArrow = new PathWithArrow(pathList.toArray(new Path[pathList.size()]), startArrowPath, endArrowPath);
			startArrowPath = null;
			endArrowPath = null;
			return pathWithArrow;
		}
		catch(Exception e)
		{
			return  null;
		}		
	}
	
	/**
	 * 
	 * @param pathContext
	 * @return
	 */
	private byte nextCommand(String pathContext)
	{
		builder.delete(0, builder.length());
		
		while(index < pathContext.length() && Character.isLetter(pathContext.charAt(index)))
		{
			builder.append(pathContext.charAt(index++));
		}
		
		String comm = builder.toString();
		if(comm.contains("h"))
		{
			//eg.ha,hb,hc,hd,he,hf,hg,hh,hi
			comm = comm.substring(2);
		}		
		
		if("m".equalsIgnoreCase(comm))
		{
			return Command_MoveTo;
		}
		else if("l".equalsIgnoreCase(comm))
		{
			return Command_LineTo;
		}
		else if("c".equalsIgnoreCase(comm))
		{
			return Command_CurveTo;
		}
		else if("x".equalsIgnoreCase(comm))
		{
			return Command_Close;
		}
		else if("e".equalsIgnoreCase(comm))
		{
			return Command_End;
		}
		else if("t".equalsIgnoreCase(comm))
		{
			return Command_RMoveTo;
		}
		else if("r".equalsIgnoreCase(comm))
		{
			return Command_RLineTo;
		}
		else if("v".equalsIgnoreCase(comm))
		{
			return Command_RCurveTo;
		}		
		else if("nf".equalsIgnoreCase(comm))
		{
			return Command_NoFill;
		}
		else if("ns".equalsIgnoreCase(comm))
		{
			return Command_NoStroke;
		}
		else if("ae".equalsIgnoreCase(comm))
		{
			return Command_AngleEllipseTo;
		}
		else if("al".equalsIgnoreCase(comm))
		{
			return Command_AngleEllipse;
		}
		else if("at".equalsIgnoreCase(comm))
		{
			return Command_ArcTo;
		}
		else if("ar".equalsIgnoreCase(comm))
		{
			return Command_Arc;
		}
		else if("wa".equalsIgnoreCase(comm))
		{
			return Command_ClockwiseArcTo;
		}
		else if("wr".equalsIgnoreCase(comm))
		{
			return Command_ClockwiseArc;
		}
		else if("qx".equalsIgnoreCase(comm))
		{
			return Command_EllipticalQaudrantX;
		}
		else if("qy".equalsIgnoreCase(comm))
		{
			return Command_EllipticalQaudrantY;
		}
		else if("qb".equalsIgnoreCase(comm))
		{
			return Command_QuadraticBezier;
		}
		else if(comm.contains("x") || comm.contains("X"))
		{
			//eg.xm,xe...
			index = index - (comm.length() - 1);
			return Command_Close; 
		}
		return Command_Invalid;
	}
	
	private Integer[] nextParameters(String pathContext)
	{		
		paraList.clear();
		int[] point = null;
		while(hasNextPoint(pathContext))
		{
			point = nextPoint(pathContext);
			
			paraList.add(point[0]);
			paraList.add(point[1]);
		}
		return paraList.toArray(new Integer[paraList.size()]);
	}
	
	private boolean hasNextPoint(String pathContext)
	{
		return index < pathContext.length() && !Character.isLetter(pathContext.charAt(index));		
	}
	
	private int[] nextPoint(String pathContext)
	{
		int[] point = new int[2];
		
		//x
		builder.delete(0, builder.length());		
		while(index < pathContext.length() 
				&& (Character.isDigit(pathContext.charAt(index)) || pathContext.charAt(index) == '-'))
		{
			builder.append(pathContext.charAt(index++));
		}
		
		if(builder.length() > 0)
		{
			point[0] = Integer.parseInt(builder.toString());
		}
		
		//
		if(index < pathContext.length() && pathContext.charAt(index) == ',')
		{
			index++;
			//y
			builder.delete(0, builder.length());		
			while(index < pathContext.length() 
					&& (Character.isDigit(pathContext.charAt(index)) || pathContext.charAt(index) == '-'))
			{
				builder.append(pathContext.charAt(index++));
			}
			
			if(builder.length() > 0)
			{
				point[1] = Integer.parseInt(builder.toString());
			}
			
			if(index < pathContext.length() && pathContext.charAt(index) == ',')
			{
				index++;
			}
		}
		
		return point;
	}
	
	private void processPath(WPAutoShape autoshape, int lineWidth, Path path, byte command, Integer[] parameters)
	{
		ArrowPathAndTail startArrowPathAndTail = null;
		ArrowPathAndTail endArrowPathAndTail = null;
		
		if(preNodeType == NodeType_Start && autoshape != null && autoshape.getStartArrowhead())
		{
			//start arrow
			switch(command)
			{
				case Command_LineTo:
					startArrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(parameters[0], 
							parameters[1], nextNode.x, nextNode.y, autoshape.getStartArrow(), lineWidth);
					break;
					
				case Command_CurveTo:
					startArrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath(parameters[4], parameters[5], 
							parameters[2], parameters[3],parameters[0], parameters[1], nextNode.x, nextNode.y, 
							autoshape.getStartArrow(), lineWidth);
					break;
					
				case Command_RLineTo:
					startArrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(parameters[0] + nextNode.x, 
							parameters[1] + nextNode.y, nextNode.x, nextNode.y, autoshape.getStartArrow(), lineWidth);
					break;
					
				case Command_RCurveTo:
					startArrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath(parameters[4] + nextNode.x, parameters[5] + nextNode.y, 
							parameters[2] + nextNode.x, parameters[3] + nextNode.y,parameters[0] + nextNode.x, parameters[1] + nextNode.y, 
							nextNode.x, nextNode.y, autoshape.getStartArrow(), lineWidth);
					break;
			}
		}
		
		if(currentNodeType == NodeType_End && autoshape != null && autoshape.getEndArrowhead())
		{
			int cnt = parameters.length;
			//end arrow
			switch(command)
			{
				case Command_LineTo:
					if(cnt > 2)
					{
						//more than two nodes
						endArrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(parameters[cnt - 4], parameters[cnt - 3], 
								parameters[cnt - 2], parameters[cnt - 1],
								autoshape.getEndArrow(), lineWidth);
					}
					else
					{
						endArrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(nextNode.x, nextNode.y, 
								parameters[cnt - 2], parameters[cnt - 1],
								autoshape.getEndArrow(), lineWidth);
					}					
					break;
					
				case Command_CurveTo:
					if(cnt > 6)
					{
						//more than two nodes
						endArrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath(parameters[cnt - 8], parameters[cnt - 7],
								parameters[cnt - 6], parameters[cnt - 5], 
								parameters[cnt - 4], parameters[cnt - 3], 
								parameters[cnt - 2], parameters[cnt - 1], 
								autoshape.getEndArrow(), lineWidth);
					}
					else
					{
						endArrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath( nextNode.x, nextNode.y,
								parameters[cnt - 6], parameters[cnt - 5], 
								parameters[cnt - 4], parameters[cnt - 3], 
								parameters[cnt - 2], parameters[cnt - 1], 
								autoshape.getEndArrow(), lineWidth);
					}					
					break;
					
				case Command_RLineTo:
					if(cnt > 2)
					{
						//more than two nodes
						endArrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(parameters[cnt - 4] + nextNode.x, parameters[cnt - 3] + nextNode.y, 
								parameters[cnt - 2] + nextNode.x, parameters[cnt - 1] + nextNode.y, 
								autoshape.getEndArrow(), lineWidth);
					}
					else
					{
						endArrowPathAndTail = LineArrowPathBuilder.getDirectLineArrowPath(nextNode.x, nextNode.y, 
								parameters[cnt - 2] + nextNode.x, parameters[cnt - 1] + nextNode.y, 
								autoshape.getEndArrow(), lineWidth);
					}
					break;
					
				case Command_RCurveTo:
					if(cnt > 6)
					{
						endArrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath(parameters[cnt - 8] + nextNode.x, parameters[cnt - 7] + nextNode.y, 
								parameters[cnt - 6] + nextNode.x, parameters[cnt - 5] + nextNode.y, 
								parameters[cnt - 4] + nextNode.x, parameters[cnt - 3] + nextNode.y, 
								parameters[cnt - 2] + nextNode.x, parameters[cnt - 1] + nextNode.y,
								autoshape.getEndArrow(), lineWidth);
					}
					else
					{
						endArrowPathAndTail = LineArrowPathBuilder.getCubicBezArrowPath(nextNode.x, nextNode.y, 
								parameters[cnt - 6] + nextNode.x, parameters[cnt - 5] + nextNode.y, 
								parameters[cnt - 4] + nextNode.x, parameters[cnt - 3] + nextNode.y, 
								parameters[cnt - 2] + nextNode.x, parameters[cnt - 1] + nextNode.y,
								autoshape.getEndArrow(), lineWidth);
					}
					break;
			}
		}

		if(startArrowPathAndTail != null)
		{
			startArrowPath = startArrowPathAndTail.getArrowPath();
            
			path.reset();
			PointF pos = LineArrowPathBuilder.getReferencedPosition(nextNode.x, nextNode.y, startArrowPathAndTail.getArrowTailCenter().x, startArrowPathAndTail.getArrowTailCenter().y, autoshape.getStartArrowType());
			
			path.moveTo(pos.x, pos.y);
		}
		
		if(endArrowPathAndTail != null)
		{
			endArrowPath = endArrowPathAndTail.getArrowPath();
            
			int cnt = parameters.length;
			PointF pos = LineArrowPathBuilder.getReferencedPosition(parameters[cnt - 2], parameters[cnt - 1], endArrowPathAndTail.getArrowTailCenter().x, endArrowPathAndTail.getArrowTailCenter().y, autoshape.getEndArrowType());
			parameters[cnt - 2] = (int)pos.x;
			parameters[cnt - 1] = (int)pos.y;
		}
		
		switch(command)
		{
			case Command_MoveTo:
				processCommand_MoveTo(path, parameters);
				break;
				
			case Command_LineTo:
				processCommand_LineTo(path, parameters);
				break;
				
			case Command_CurveTo:
				processCommand_CurveTo(path, parameters);
				break;
				
			case Command_RMoveTo:
				processCommand_rMoveTo(path, parameters);
				break;
				
			case Command_RLineTo:
				processCommand_rLineTo(path, parameters);
				break;
				
			case Command_RCurveTo:
				processCommand_rCurveTo(path, parameters);
				break;
				
			case Command_Close:
				path.close();
				break;
		}
	}
	
	private void processCommand_MoveTo(Path path, Integer[] parameters)
	{
		float x = 0;
		float y = 0;
		if(parameters.length == 2)
		{
			x = parameters[0];
			y = parameters[1];
		}
		else if(parameters.length == 1)
		{
			x = parameters[0];
		}
		
		path.moveTo(x, y);
		
		nextNode.set(x, y);	
	}
	
	private void processCommand_LineTo(Path path, Integer[] parameters)
	{
		int paraIndex = 0;
		while(paraIndex < parameters.length - 1)
		{
			path.lineTo(parameters[paraIndex], parameters[paraIndex + 1]);
			
			preNode.set(nextNode);
			nextNode.set(parameters[paraIndex], parameters[paraIndex + 1]);
			
			paraIndex += 2;
		}
	}
	
	private void processCommand_CurveTo(Path path, Integer[] parameters)
	{
		int paraIndex = 0;
		while(paraIndex < parameters.length - 5)
		{
			path.cubicTo(parameters[paraIndex], parameters[paraIndex + 1], 
					parameters[paraIndex + 2], parameters[paraIndex + 3], 
					parameters[paraIndex + 4], parameters[paraIndex + 5]);
			
			preNode.set(nextNode);
			ctrNode1.set(parameters[paraIndex], parameters[paraIndex + 1]);
			ctrNode2.set(parameters[paraIndex + 2], parameters[paraIndex + 3]);
			nextNode.set(parameters[paraIndex + 4], parameters[paraIndex + 5]);
			
			paraIndex += 6;
		}
	}
	
	/**
	 * relative move to
	 * @param path
	 * @param parameters
	 */
	private void processCommand_rMoveTo(Path path, Integer[] parameters)
	{
		if(parameters.length == 2)
		{
			path.rMoveTo(parameters[0], parameters[1]);
			
			preNode.set(nextNode);
			nextNode.offset(parameters[0], parameters[1]);
					
		}
		else if(parameters.length == 1)
		{
			path.rMoveTo(parameters[0], 0);
			
			preNode.set(nextNode);
			nextNode.offset(parameters[0], 0);
		}
		else 
		{
			path.rMoveTo(0, 0);
			
			preNode.set(nextNode);
			nextNode.offset(0, 0);
		}
	} 
	
	private void processCommand_rLineTo(Path path, Integer[] parameters)
	{
		int paraIndex = 0;
		while(paraIndex < parameters.length - 1)
		{
			path.rLineTo(parameters[paraIndex], parameters[paraIndex + 1]);
			
			preNode.set(nextNode);
			nextNode.offset(parameters[paraIndex], parameters[paraIndex + 1]);
			
			paraIndex += 2;
		}
	}
	
	private void processCommand_rCurveTo(Path path, Integer[] parameters)
	{
		int paraIndex = 0;
		while(paraIndex < parameters.length - 5)
		{
			path.rCubicTo(parameters[paraIndex], parameters[paraIndex + 1], 
					parameters[paraIndex + 2], parameters[paraIndex + 3], 
					parameters[paraIndex + 4], parameters[paraIndex + 5]);
			
			preNode.set(nextNode);
			ctrNode1.offset(parameters[paraIndex], parameters[paraIndex + 1]);
			ctrNode2.offset(parameters[paraIndex + 2], parameters[paraIndex + 3]);
			nextNode.offset(parameters[paraIndex + 4], parameters[paraIndex + 5]);
			
			paraIndex += 6;
		}
	}
	
	//path context index
	private int index;
	
	private StringBuilder builder = new StringBuilder();	
	private List<Integer> paraList = new ArrayList<Integer>();
}

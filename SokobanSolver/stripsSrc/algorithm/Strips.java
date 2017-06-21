package algorithm;

import java.util.LinkedList;
import java.util.Stack;

import gameObjects.Position2D;

public class Strips<T> implements Planner<T> {
//	@Override
//	public Plan<T> plan(Plannable<T> plannable) {
//		LinkedList<Action<T>> actionsToDo=new LinkedList<Action<T>>();
//		Stack<Predicate<T>> stack = new Stack<>();
//		stack.push(plannable.getGoal());
//		
//		while(!stack.isEmpty())
//		{
//			Predicate<T> top=stack.peek();
//			System.out.println(stack);
//			if(top == null)
//				stack.pop();
//			else if(!(top instanceof Action) || !(top instanceof LinkAction) )
//			{
//				if(!plannable.satisfies(plannable.getKnowledgebase(), top))
//				{
//					Action<T> action=null;
//					if(top instanceof AndPredicate)//if complex then unpack components to stack
//					{
//						AndPredicate<T> andPred=(AndPredicate<T>)top;
//						for (Predicate<T> predicate : andPred.getComponents()) {
//							stack.push(predicate);
//						}
//					}
//					
//					else if(top instanceof SimplePredicate)//
//					{
//						stack.pop();
//						action=plannable.getSatisfyingAction(top);//Change to a set of actions
//						if(action instanceof LinkAction)
//						{
//							LinkAction<T> linkAc = (LinkAction<T>)action;
//							AndPredicate<T> precon = new AndPredicate<T>(((Action<T>)linkAc.getActions().getFirst()).getPreconditions());
//							AndPredicate<T> effects = new AndPredicate<T>(((Action<T>)linkAc.getActions().getFirst()).getEffects());
//							Action<T> newAc = new Action<T>("Move_MainCharacter_To", precon, effects);
//							LinkAction<T> link=(LinkAction<T>)action;
//							stack.push(newAc);
//							}
//						}
//				}
//				else//satisfied
//				{
//					stack.pop();
//				}
//			}
//			else//top is an action
//			{//lets just take the strips from the right
//				
//				stack.pop();//remove from top
//				Action<T> a=(Action<T>) top;
//				plannable.getKnowledgebase().update(a.getEffects(),plannable);//simulate execution
//				actionsToDo.add(a);
//			}
//		}
//		//System.out.println(actionsToDo);
//		return new Plan<T>(actionsToDo);
//	}
	
	@Override
	public Plan<T> plan(Plannable<T> plannable) {
		LinkedList<Action<T>> actionsToDo=new LinkedList<Action<T>>();
		Stack<Predicate<T>> stack = new Stack<>();
		stack.push(plannable.getGoal());
		while(!stack.isEmpty())
		{
			Predicate<T> top=stack.peek();
			if(!(top instanceof Action))
			{
				if(!plannable.satisfies(plannable.getKnowledgebase(), top))
				{
					if(top instanceof AndPredicate)//if complex then unpack components to stack
					{
						//TODO: add a way to decide order of insertion (comparator)
						AndPredicate<T> andPred=(AndPredicate<T>)top;
						for (Predicate<T> predicate : andPred.getComponents()) {
							stack.push(predicate);
						}
					}
					else if(top instanceof SimplePredicate)//
					{
						stack.pop();
						Action<T> action=plannable.getSatisfyingAction(top);//Change to a set of actions
						//TODO: 
						stack.push(action);
						stack.push(action.getPreconditions());
					}
				}
				else//satisfied
				{
					stack.pop();
				}
			}
			else//top is an action
			{
				stack.pop();//remove from top
				Action<T> a=(Action<T>) top;
				plannable.getKnowledgebase().update(a.getEffects(),plannable);
				actionsToDo.add(a);
			}
		}
		System.out.println(actionsToDo);
		return new Plan<T>(actionsToDo);
	}


}

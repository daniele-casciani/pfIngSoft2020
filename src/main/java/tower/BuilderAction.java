package tower;

class BuilderAction implements Action {

	@Override
	public void movement(Level start, Level end) {
		Level NewStart=((Builder)((Floor) start)).move(end);
//dobbiamo rimettere a posto i due elementi
	}

	@Override
	public void buildcell(Level level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newBuilder(Level level,String color) {
		Builder builder=new Builder(level, color);
		//inserimento in mappa	
	}

	@Override
	public  void killBuilder(Level dead) {
		Level cleanfloor=((Builder)((Floor) dead)).kill();
		//inserimento in mappa
	}
	

}

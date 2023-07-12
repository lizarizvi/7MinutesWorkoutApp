package com.example.a7minutesworkoutapp

object Constants {

    fun defaultExerciseList() : ArrayList<ExerciseModel>{
        var exerciseList = ArrayList<ExerciseModel>()

        val armCircles = ExerciseModel(1,"Arm Circles",R.drawable.armcircle,false,false)
        exerciseList.add(armCircles)

        val punches = ExerciseModel(2,"Punches",R.drawable.punches,false,false)
        exerciseList.add(punches)

        val prayerPushes = ExerciseModel(3,"Prayer Pushes",R.drawable.prayerpushes,false,false)
        exerciseList.add(prayerPushes)

        val sideBends = ExerciseModel(4,"Side Bends",R.drawable.sidebends,false,false)
        exerciseList.add(sideBends)

        val highKneeJacks = ExerciseModel(5,"High Knee Jacks",R.drawable.highkneejacks,false,false)
        exerciseList.add(highKneeJacks)

        val kneeRaises = ExerciseModel(6,"Knee Raises",R.drawable.kneeraises,false,false)
        exerciseList.add(kneeRaises)

        val lateralSteps = ExerciseModel(7,"Lateral Steps",R.drawable.lateralsteps,false,false)
        exerciseList.add(lateralSteps)

        val lunges = ExerciseModel(8,"Lunges",R.drawable.lunges,false,false)
        exerciseList.add(lunges)

        val toeTapLegLifts = ExerciseModel(9,"Toe Tap Leg Lifts",R.drawable.toetapleglifts,false,false)
        exerciseList.add(toeTapLegLifts)

        val snowAngels = ExerciseModel(10,"Snow Angels",R.drawable.snowangels,false,false)
        exerciseList.add(snowAngels)

        val squats = ExerciseModel(11,"Squats",R.drawable.squats,false,false)
        exerciseList.add(squats)

        val crunches = ExerciseModel(12,"Crunches",R.drawable.crunches,false,false)
        exerciseList.add(crunches)

        val planks = ExerciseModel(13,"Planks",R.drawable.planks,false,false)
        exerciseList.add(planks)

        val bicycleCrunches = ExerciseModel(14,"Bicycle Crunches",R.drawable.bicyclecrunches,false,false)
        exerciseList.add(bicycleCrunches)

        return exerciseList
    }
}
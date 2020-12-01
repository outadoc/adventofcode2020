val masses = "121656\n110933\n80850\n137398\n76307\n50450\n124691\n86449\n145386\n148648\n68909\n134697\n109636\n115718\n134485\n89267\n64829\n109070\n84257\n109010\n97574\n98363\n123029\n105568\n114500\n92041\n128869\n148350\n144605\n91862\n134417\n54710\n147843\n121914\n127855\n74545\n89596\n106562\n69863\n147082\n135724\n111637\n68869\n103685\n99453\n80908\n136020\n64974\n125159\n87504\n62499\n73294\n128811\n121567\n54673\n66647\n66871\n71228\n101622\n130675\n69025\n146118\n79970\n118267\n122279\n89523\n62965\n148036\n119625\n127056\n54980\n143581\n103274\n83064\n125131\n54362\n115851\n139103\n140674\n69616\n81353\n116441\n73898\n51403\n137019\n93146\n67273\n138182\n126680\n148683\n127805\n111741\n102219\n99603\n90453\n147581\n102136\n109913\n144899\n140572"
        .lines()
        .asSequence()
        .filter { it.isNotBlank() }
        .map { it.toDouble() }

fun fuelNeededForMass(mass: Double) = Math.floor(mass / 3) - 2

fun step1(): Long = masses
        .map { mass -> fuelNeededForMass(mass) }
        .sum()
        .toLong()

fun fuelNeededForMassAndFuel(mass: Double): Double {
    val fuelNeeded = fuelNeededForMass(mass)

    if (fuelNeeded <= 0) {
        return .0
    }

    return fuelNeeded + fuelNeededForMassAndFuel(fuelNeeded)
}

fun step2(): Long {
    return masses
            .map { mass -> fuelNeededForMassAndFuel(mass) }
            .sum()
            .toLong()

}

println(step1())
println(step2())

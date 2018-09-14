import { AppModel, UpdateStream, Actions } from '../models'

export const createActions = (update: UpdateStream): Actions => ({
  inc: (amount: number) =>
    update((model: AppModel) =>
      Object.assign(model, { counter: model.counter + amount })
    ),

  randomize: (text: string) =>
    update((model: AppModel) => ({
      ...model,
      randomText: `${text}${Math.random().toString()}`
    }))
})

import * as ms from 'mithril/stream'
import * as m from 'mithril'

export class AppModel {
  counter: number = 0
  randomText: string = 'random text initial value'
}

export interface Actions {
  inc: (v: number) => void
  randomize: (t: string) => void
}

/**
 * Meiosis related types
 */

export type ModelUpdateFunction = (model: AppModel) => AppModel
export type ModelStream = ms.Stream<AppModel>
export type UpdateStream = ms.Stream<ModelUpdateFunction>

export interface MeiosisApp {
  /**
   * @returns the inital model of the app
   */
  model: () => AppModel

  /**
   * Called after every change in the model with the new model.
   * @param model The new model from which the new view will be generated.
   * @returns The new view that will be passed to the `render` method.
   */
  view: (model: AppModel) => m.Vnode

  /**
   * @param el The element the app is rendered to in the DOM
   * @param v The generated view node that will be put into the DOM
   */
  render: (el: HTMLElement, v: m.Vnode) => void
}

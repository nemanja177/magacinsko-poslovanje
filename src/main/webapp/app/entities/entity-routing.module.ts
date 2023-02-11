import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'preduzece',
        data: { pageTitle: 'Preduzeces' },
        loadChildren: () => import('./preduzece/preduzece.module').then(m => m.PreduzeceModule),
      },
      {
        path: 'poslovni-partner',
        data: { pageTitle: 'PoslovniPartners' },
        loadChildren: () => import('./poslovni-partner/poslovni-partner.module').then(m => m.PoslovniPartnerModule),
      },
      {
        path: 'radnik',
        data: { pageTitle: 'Radniks' },
        loadChildren: () => import('./radnik/radnik.module').then(m => m.RadnikModule),
      },
      {
        path: 'magacin',
        data: { pageTitle: 'Magacins' },
        loadChildren: () => import('./magacin/magacin.module').then(m => m.MagacinModule),
      },
      {
        path: 'artikal',
        data: { pageTitle: 'Artikals' },
        loadChildren: () => import('./artikal/artikal.module').then(m => m.ArtikalModule),
      },
      {
        path: 'jedinica-mere',
        data: { pageTitle: 'JedinicaMeres' },
        loadChildren: () => import('./jedinica-mere/jedinica-mere.module').then(m => m.JedinicaMereModule),
      },
      {
        path: 'magacinska-kartica',
        data: { pageTitle: 'MagacinskaKarticas' },
        loadChildren: () => import('./magacinska-kartica/magacinska-kartica.module').then(m => m.MagacinskaKarticaModule),
      },
      {
        path: 'poslovan-godina',
        data: { pageTitle: 'PoslovanGodinas' },
        loadChildren: () => import('./poslovan-godina/poslovan-godina.module').then(m => m.PoslovanGodinaModule),
      },
      {
        path: 'popis',
        data: { pageTitle: 'Popis' },
        loadChildren: () => import('./popis/popis.module').then(m => m.PopisModule),
      },
      {
        path: 'stavka-popisa',
        data: { pageTitle: 'StavkaPopisas' },
        loadChildren: () => import('./stavka-popisa/stavka-popisa.module').then(m => m.StavkaPopisaModule),
      },
      {
        path: 'promet-magacinske-kartice',
        data: { pageTitle: 'PrometMagacinskeKartices' },
        loadChildren: () =>
          import('./promet-magacinske-kartice/promet-magacinske-kartice.module').then(m => m.PrometMagacinskeKarticeModule),
      },
      {
        path: 'analiticka-magacinska-kartica',
        data: { pageTitle: 'AnalitickaMagacinskaKarticas' },
        loadChildren: () =>
          import('./analiticka-magacinska-kartica/analiticka-magacinska-kartica.module').then(m => m.AnalitickaMagacinskaKarticaModule),
      },
      {
        path: 'stavka-prometnog-dokumenta',
        data: { pageTitle: 'StavkaPrometnogDokumentas' },
        loadChildren: () =>
          import('./stavka-prometnog-dokumenta/stavka-prometnog-dokumenta.module').then(m => m.StavkaPrometnogDokumentaModule),
      },
      {
        path: 'prometni-dokument',
        data: { pageTitle: 'PrometniDokuments' },
        loadChildren: () => import('./prometni-dokument/prometni-dokument.module').then(m => m.PrometniDokumentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
